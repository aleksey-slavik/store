package com.globallogic.store.rest;

import com.globallogic.store.converter.user.AuthorityConverter;
import com.globallogic.store.converter.user.UserConverter;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.user.Authority;
import com.globallogic.store.domain.user.AuthorityName;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.user.AuthorityDTO;
import com.globallogic.store.dto.user.UserDTO;
import com.globallogic.store.security.RegisterUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Rest controller for operations with users
 *
 * @author oleksii.slavik
 */
@RestController
@RequestMapping(value = "/api/users")
@Api(value = "/api/users", description = "Operations with users")
public class UserRestController {

    /**
     * user DAO
     */
    private GenericDao<User> userDao;

    /**
     * user authority DAO
     */
    private GenericDao<Authority> authorityDao;

    /**
     * user converter
     */
    private UserConverter userConverter;

    /**
     * authority converter
     */
    private AuthorityConverter authorityConverter;

    /**
     * Registration service
     */
    private RegisterUserService registerUserService;

    public UserRestController(
            GenericDao<User> userDao,
            GenericDao<Authority> authorityDao,
            UserConverter userConverter,
            AuthorityConverter authorityConverter,
            RegisterUserService registerUserService) {
        this.userDao = userDao;
        this.authorityDao = authorityDao;
        this.userConverter = userConverter;
        this.authorityConverter = authorityConverter;
        this.registerUserService = registerUserService;
    }

    /**
     * Resource to get a user by id
     *
     * @param id given id
     * @return user with given id
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to get a user by id",
            notes = "This can only be done by the authenticated user, which is a account owner or have admin role")
    public ResponseEntity<?> getUserById(
            @ApiParam(value = "user id", required = true) @PathVariable long id) {
        User user = userDao.getEntityByKey(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok().body(userConverter.toResource(user));
        }
    }

    /**
     * Resource to get a list of users
     *
     * @param username  username of user
     * @param firstName first name of user
     * @param lastName  last name of user
     * @param email     email of user
     * @param page      page number
     * @param size      count of items per page
     * @param sort      name of sorting column
     * @param order     sorting order
     * @return list of users
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to get a list of users",
            notes = "This can only be done by the authenticated user, which have admin role")
    public ResponseEntity<?> getUserList(
            @ApiParam(value = "username of user") @RequestParam(value = "username", required = false) String[] username,
            @ApiParam(value = "first name of user") @RequestParam(value = "firstName", required = false) String[] firstName,
            @ApiParam(value = "last name of user") @RequestParam(value = "lastName", required = false) String[] lastName,
            @ApiParam(value = "email of user") @RequestParam(value = "email", required = false) String[] email,
            @ApiParam(value = "page number", defaultValue = "1") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "count of items per page", defaultValue = "5") @RequestParam(value = "size", defaultValue = "5") int size,
            @ApiParam(value = "name of sorting column", defaultValue = "id") @RequestParam(value = "sort", defaultValue = "id") String sort,
            @ApiParam(value = "sorting order", defaultValue = "asc") @RequestParam(value = "order", defaultValue = "asc") String order) {

        SearchCriteria criteria = new SearchCriteria()
                .criteria("username", (Object[]) username)
                .criteria("firstName", (Object[]) firstName)
                .criteria("lastName", (Object[]) lastName)
                .criteria("email", (Object[]) email)
                .offset(page)
                .limit(size)
                .sortBy(sort)
                .order(order);

        List<User> users = userDao.getEntityList(criteria);

        if (users == null || users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok().body(userConverter.toResources(users));
        }
    }

    /**
     * Resource to create a user
     *
     * @param user created user object
     * @return created user
     */
    @PreAuthorize("isAnonymous() || hasRole('ADMIN')")
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to create a user",
            notes = "This can only be done by the anonymous user or user, which have admin role")
    public ResponseEntity<?> createUser(
            @ApiParam(value = "auto login") @RequestParam(value = "autoLogin", required = false) boolean autoLogin,
            @ApiParam(value = "created user object", required = true) @Valid @RequestBody UserDTO user) {

        User created = userDao.createEntity(userConverter.toOrigin(user));
        SearchCriteria criteria = new SearchCriteria().criteria("title", AuthorityName.CUSTOMER);
        Authority granted = authorityDao.getEntity(criteria);
        created.appendAuthority(granted);
        created.setEnabled(true);
        userDao.updateEntity(created);

        if (autoLogin) {
            registerUserService.autoLogin(created.getUsername(), created.getPassword());
        }

        return ResponseEntity.ok().body(userConverter.toResource(created));
    }

    /**
     * Resource to update a user
     *
     * @param id   user id
     * @param user updated user object
     * @return updated user
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to update a user",
            notes = "This can only be done by the authenticated user")
    public ResponseEntity<?> updateUser(
            @ApiParam(value = "user id", required = true) @PathVariable long id,
            @ApiParam(value = "updated user object", required = true) @Valid @RequestBody UserDTO user) {

        Set<Authority> authorities = new HashSet<>();
        authorities.addAll(userDao.getEntityByKey(id).getAuthorities());
        user.setId(id);
        User updated = userConverter.toOrigin(user);
        updated.setAuthorities(authorities);
        updated = userDao.updateEntity(updated);
        return ResponseEntity.ok().body(userConverter.toResource(updated));
    }

    /**
     * Resource to delete a user
     *
     * @param id user id
     * @return deleted user
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to delete a user",
            notes = "This can only be done by the authenticated user")
    public ResponseEntity<?> deleteUserById(
            @ApiParam(value = "user id", required = true) @PathVariable long id) {

        User deleted = userDao.deleteEntityByKey(id);
        return ResponseEntity.ok().body(userConverter.toResource(deleted));
    }

    /**
     * Resource to get all user authorities
     *
     * @param id user id
     * @return authorities of user with given id
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/{id}/authorities",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to get all user authorities",
            notes = "This can only be done by user with admin role")
    public ResponseEntity<?> getUserAuthorities(
            @ApiParam(value = "user id", required = true) @PathVariable long id) {

        List<Authority> authorities = new ArrayList<>();
        authorities.addAll(userDao.getEntityByKey(id).getAuthorities());
        return ResponseEntity.ok().body(authorityConverter.toResources(authorities));
    }

    /**
     * Resource to append authority to user
     *
     * @param id        user id
     * @param authority authority object
     * @return granted authority
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/{id}/authorities",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to append authority to user",
            notes = "This can only be done by user with admin role")
    public ResponseEntity<?> appendUserAuthority(
            @ApiParam(value = "user id", required = true) @PathVariable long id,
            @ApiParam(value = "authority object", required = true) @RequestBody AuthorityDTO authority) {

        SearchCriteria criteria = new SearchCriteria().criteria("title", authority.getTitle());
        Authority granted = authorityDao.getEntity(criteria);
        User updated = userDao.getEntityByKey(id);
        updated.appendAuthority(granted);
        userDao.updateEntity(updated);
        return ResponseEntity.ok().body(authorityConverter.toResource(granted));
    }

    /**
     * Resource to delete authority from user
     *
     * @param id     user id
     * @param authId authority id
     * @return deleted authority
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/{id}/authorities/{authId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to delete authority from user",
            notes = "This can only be done by user with admin role")
    public ResponseEntity<?> deleteUserAuthority(
            @ApiParam(value = "user id", required = true) @PathVariable long id,
            @ApiParam(value = "authority id", required = true) @PathVariable long authId) {

        Authority removed = authorityDao.getEntityByKey(authId);
        User updated = userDao.getEntityByKey(id);
        updated.removeAuthority(removed);
        userDao.updateEntity(updated);
        return ResponseEntity.ok().body(authorityConverter.toResource(removed));
    }
}
