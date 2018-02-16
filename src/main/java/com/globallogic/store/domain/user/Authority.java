package com.globallogic.store.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role", schema = "public")
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private long id;

    @Column(name = "title", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityName title;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities")
    private Set<User> users = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AuthorityName getTitle() {
        return title;
    }

    public void setTitle(AuthorityName title) {
        this.title = title;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
