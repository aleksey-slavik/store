package com.globallogic.store.util;

import com.globallogic.store.Workflow;
import com.globallogic.store.security.AuthenticatedUser;
import com.globallogic.store.security.jwt.TokenUtil;
import io.jsonwebtoken.Clock;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Check correct work of jwt util class
 *
 * @author oleksii.slavik
 */
public class TokenUtilTest {

    /**
     * Mocked {@link Clock} object for manipulate {@link Date} data
     */
    @Mock
    private Clock clock;

    /**
     * Injecting {@link TokenUtil} object
     */
    @InjectMocks
    private TokenUtil tokenUtil;

    /**
     * Error value for comparison {@link Date} data
     */
    private static final long DELTA = 1000L;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        //insert test field data
        ReflectionTestUtils.setField(tokenUtil, "secret", "testSecret");
        ReflectionTestUtils.setField(tokenUtil, "expiration", 3600000L);
        ReflectionTestUtils.setField(tokenUtil, "userIdKey", "userId");
        ReflectionTestUtils.setField(tokenUtil, "userRoleKey", "userRole");
        ReflectionTestUtils.setField(tokenUtil, "userCredentialsKey", "userCredentials");
    }

    /**
     * Check that tokens, which created at different times, are different
     */
    @Test
    public void checkTokenDateTest() {
        when(clock.now())
                .thenReturn(DateUtil.yesterday())
                .thenReturn(DateUtil.now());
        final String earlyToken = generateToken();
        final String laterToken = generateToken();
        assertNotEquals(earlyToken, laterToken);
    }

    /**
     * Check that username from token is equals to username of user
     */
    @Test
    public void checkUsernameFromToken() {
        when(clock.now())
                .thenReturn(DateUtil.now());
        AuthenticatedUser user = Workflow.createAdminAuthenticatedUser();
        final String token = generateToken(user);
        assertEquals(user.getUsername(), tokenUtil.getUsernameFromToken(token));
    }

    /**
     * Check that id from token is equals to id of user
     */
    @Test
    public void checkIdFromToken() {
        when(clock.now())
                .thenReturn(DateUtil.now());
        AuthenticatedUser user = Workflow.createAdminAuthenticatedUser();
        final String token = generateToken(user);
        assertEquals(user.getId(), tokenUtil.getIdFromToken(token));
    }

    /**
     * Check that role from token is equals to role of user
     */
    @Test
    public void checkRolesFromToken() {
        when(clock.now())
                .thenReturn(DateUtil.now());
        AuthenticatedUser user = Workflow.createAdminAuthenticatedUser();
        final String token = generateToken(user);
        assertTrue(user.getAuthorities().containsAll(tokenUtil.getRoleFromToken(token)));
    }

    /**
     * Check that role from token is equals to role of user
     */
    @Test
    public void checkCredentialsFromToken() {
        when(clock.now())
                .thenReturn(DateUtil.now());
        AuthenticatedUser user = Workflow.createAdminAuthenticatedUser();
        final String token = generateToken(user);
        assertEquals(user.getPassword(), tokenUtil.getCredentialsFromToken(token));
    }

    /**
     * Check that created time from token is close to created time of user
     */
    @Test
    public void checkIssuedAtFromToken() {
        final Date created = DateUtil.now();
        when(clock.now())
                .thenReturn(created);
        final String token = generateToken();
        assertTrue(DateUtil.timeDifference(tokenUtil.getIssuedAtFromToken(token), created) < DELTA);
    }

    /**
     * Check that created time from token is close to created time of user
     */
    @Test
    public void checkExpirationFromToken() {
        final Date created = DateUtil.now();
        final Date expiration = new Date(created.getTime() + 3600000L);
        when(clock.now())
                .thenReturn(created);
        final String token = generateToken();
        assertTrue(DateUtil.timeDifference(tokenUtil.getExpirationFromToken(token), expiration) < DELTA);
    }

    /**
     * Check valid and invalid tokens
     */
    @Test
    public void checkTokenValidation() {
        when(clock.now())
                .thenReturn(DateUtil.now());
        AuthenticatedUser user = Workflow.createAdminAuthenticatedUser();
        final String validToken = generateToken(user);
        assertTrue(tokenUtil.validateToken(validToken, user));

        when(clock.now())
                .thenReturn(DateUtil.now())
                .thenReturn(DateUtil.tomorrow());
        final String invalidToken = generateToken(user);
        assertFalse(tokenUtil.validateToken(invalidToken, user));
    }

    /**
     * Check that user data not change during refresh token
     */
    @Test
    public void checkRefreshUser() {
        when(clock.now())
                .thenReturn(DateUtil.now());

        AuthenticatedUser user = Workflow.createAdminAuthenticatedUser();
        final String createdToken = generateToken(user);
        assertEquals(user, tokenUtil.parseToken(createdToken));

        final String refreshedToken = tokenUtil.refreshToken(createdToken);
        assertEquals(user, tokenUtil.parseToken(refreshedToken));
    }

    /**
     * Create token for test admin {@link AuthenticatedUser} object.
     *
     * @return token for test admin user
     */
    private String generateToken() {
        AuthenticatedUser user = Workflow.createAdminAuthenticatedUser();
        return this.generateToken(user);
    }

    /**
     * Create token for given {@link AuthenticatedUser} object.
     *
     * @param user given {@link AuthenticatedUser} object
     * @return token for test admin user
     */
    private String generateToken(AuthenticatedUser user) {
        return tokenUtil.generateToken(user);
    }
}
