package com.globallogic.store.domain.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Domain object that represents a authority entity
 *
 * @author oleksii.slavik
 */
@Entity
@Table(name = "role", schema = "public")
public class Authority implements Serializable {

    /**
     * authority id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private long id;

    /**
     * authority title
     */
    @Column(name = "title", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityName title;

    /*@ManyToMany(mappedBy = "authorities")
    private Set<User> users = new HashSet<>();*/

    /**
     * @return authority id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id authority id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return authority title
     */
    public AuthorityName getTitle() {
        return title;
    }

    /**
     * @param title authority title
     */
    public void setTitle(AuthorityName title) {
        this.title = title;
    }

    /*public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        return id == authority.id && title == authority.title;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        return result;
    }
}
