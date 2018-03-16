package com.globallogic.store.domain.permission;

import javax.persistence.*;

/**
 * Domain object that represents a access permissions
 *
 * @author oleksii.slavik
 */
@Entity
@Table(name = "permission", schema = "public")
public class Permission {

    /**
     * permission id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private long id;

    /**
     * object class
     */
    @Column(name = "object_class", nullable = false)
    private String objectClass;

    /**
     * object id
     */
    @Column(name = "object_id", nullable = false)
    private long objectId;

    /**
     * user username
     */
    @Column(name = "sid", nullable = false)
    private String sid;

    /**
     * granted permission
     */
    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionName permission;

    /**
     * @return permission id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id permission id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return object class
     */
    public String getObjectClass() {
        return objectClass;
    }

    /**
     * @param objectClass object class
     */
    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    /**
     * @return object id
     */
    public long getObjectId() {
        return objectId;
    }

    /**
     * @param objectId object id
     */
    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    /**
     * @return user username
     */
    public String getSid() {
        return sid;
    }

    /**
     * @param sid user username
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * @return granted permission
     */
    public PermissionName getPermission() {
        return permission;
    }

    /**
     * @param permission granted permission
     */
    public void setPermission(PermissionName permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", objectClass='" + objectClass + '\'' +
                ", objectId=" + objectId +
                ", sid='" + sid + '\'' +
                ", permission=" + permission +
                '}';
    }
}
