package com.globallogic.store.rest;

import com.globallogic.store.assembler.user.AuthorityAssembler;
import com.globallogic.store.assembler.user.UserAssembler;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.SearchCriteria;
import com.globallogic.store.domain.user.Authority;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.user.AuthorityDto;
import com.globallogic.store.dto.user.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.validation.Valid;
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
     * {@link User} DAO
     */
    private GenericDao<User> userDao;

    /**
     * {@link Authority} DAO
     */
    private GenericDao<Authority> authorityDao;

    /**
     * Resource assembler to convert {@link User} to {@link UserDto}
     */
    private UserAssembler userAssembler;
    /**
     * Resource assembler to convert {@link User} to {@link com.globallogic.store.dto.user.AuthorityDto}
     */
    private AuthorityAssembler authorityAssembler;

    public UserRestController(
            GenericDao<User> userDao,
            GenericDao<Authority> authorityDao,
            UserAssembler userAssembler,
            AuthorityAssembler authorityAssembler) {
        this.userDao = userDao;
        this.authorityDao = authorityDao;
        this.userAssembler = userAssembler;
        this.authorityAssembler = authorityAssembler;
    }

    /**
     * Resource to get a user by id
     *
     * @param id given id
     * @return {@link User} item
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to get a user by id")
    public ResponseEntity<?> getUserById(
            @ApiParam(value = "user id", required = true) @PathVariable long id) {
        User user = userDao.getEntityByKey(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok().body(userAssembler.toResource(user));
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
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to get a list of users")
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
            return ResponseEntity.ok().body(userAssembler.toResources(users));
        }
    }

    /**
     * Resource to create a user
     *
     * @param user created user object
     * @return created user
     */
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to create a user")
    public ResponseEntity<?> createUser(
            @ApiParam(value = "created user object", required = true) @Valid @RequestBody UserDto user) {
        if (checkUser(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } else {
            User created = userDao.createEntity(userAssembler.toResource(user));
            return ResponseEntity.ok().body(userAssembler.toResource(created));
        }
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
            @ApiParam(value = "updated user object", required = true) @Valid @RequestBody UserDto user) {
        if (checkUser(id, user.getUsername()) != null) {
            user.setUserId(id);
            User updated = userDao.updateEntity(userAssembler.toResource(user));
            return ResponseEntity.ok().body(userAssembler.toResource(updated));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
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
        if (checkUser(id) != null) {
            User deleted = userDao.deleteEntity(id);
            return ResponseEntity.ok().body(userAssembler.toResource(deleted));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    /**
     * Resource to get all user authorities
     *
     * @param id user id
     * @return authorities of user with given id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            value = "/{id}/authorities",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to get all user authorities",
            notes = "This can only be done by user with admin role")
    public ResponseEntity<?> getUserAuthorities(
            @ApiParam(value = "user id", required = true) @PathVariable long id) {
        if (checkUser(id) != null) {
            Set<Authority> authorities = userDao.getEntityByKey(id).getAuthorities();
            return ResponseEntity.ok().body(authorityAssembler.toResources(authorities));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    /**
     * Resource to append authority to user
     *
     * @param id        user id
     * @param authority authority object
     * @return granted authority
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            value = "/{id}/authorities",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to append authority to user",
            notes = "This can only be done by user with admin role")
    public ResponseEntity<?> appendUserAuthority(
            @ApiParam(value = "user id", required = true) @PathVariable long id,
            @ApiParam(value = "authority object", required = true) @RequestBody AuthorityDto authority) {
        User updated = checkUser(id);

        if (updated != null) {
            SearchCriteria criteria = new SearchCriteria().criteria("title", authority.getTitle());
            Authority granted = authorityDao.getEntity(criteria);
            updated.appendAuthority(granted);
            userDao.updateEntity(updated);
            return ResponseEntity.ok().body(authorityAssembler.toResource(granted));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    /**
     * Resource to delete authority from user
     *
     * @param id     user id
     * @param authId authority id
     * @return deleted authority
     */
    @PreAuthorize("hasRole('ADMIN')")
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
        User updated = checkUser(id);

        if (updated != null) {
            Authority removed = authorityDao.getEntityByKey(authId);
            updated.removeAuthority(removed);
            userDao.updateEntity(updated);
            return ResponseEntity.ok().body(authorityAssembler.toResource(removed));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    /**
     * Check is exist user with given username
     *
     * @param username username of user
     * @return true is user with given username already exist, false in otherwise
     */
    private User checkUser(String username) {
        try {
            SearchCriteria criteria = new SearchCriteria().criteria("username", username);
            return userDao.getEntity(criteria);
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Check is exist user with given id
     *
     * @param id user id
     * @return true if user with given id already exist, false in otherwise
     */
    private User checkUser(long id) {
        try {
            SearchCriteria criteria = new SearchCriteria().criteria("id", id);
            return userDao.getEntity(criteria);
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Check is exist user with given id and username
     *
     * @param id       user id
     * @param username username of user
     * @return true if user with given id already exist, false in otherwise
     */
    private User checkUser(long id, String username) {
        try {
            SearchCriteria criteria = new SearchCriteria()
                    .criteria("id", id)
                    .criteria("username", username);
            return userDao.getEntity(criteria);
        } catch (NoResultException e) {
            return null;
        }
    }
}
