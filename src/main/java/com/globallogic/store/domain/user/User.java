package com.globallogic.store.domain.user;

import com.globallogic.store.domain.Identifiable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Domain object that represents a user entity
 *
 * @author oleksii.slavik
 */
@Entity
@Table(name = "user", schema = "public")
public class User implements Serializable, Identifiable {

    /**
     * user id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private long id;

    /**
     * user username
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * user password
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * user email
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * user first name
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * user last name
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * state of user account
     */
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    /**
     * user authorities
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Authority> authorities = new HashSet<>();

    /**
     * @return entity id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id entity id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return user username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username user username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName user first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return user last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName user last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return true if user account is active, false in otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled state of user account
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return user authorities
     */
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    /**
     * @param authorities user authorities
     */
    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    /**
     * Append given authority to current authorities
     *
     * @param authority new authority
     */
    public void appendAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    /**
     * Remove given authority from current authorities
     *
     * @param authority removed authority
     */
    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }
}
