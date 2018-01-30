package com.globallogic.store.filter;

import com.globallogic.store.model.Order;
import com.globallogic.store.model.Status;
import com.globallogic.store.model.User;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.Root;
import java.util.Map;

public class OrderSearchFilter extends SearchFilter<Order> {

    private User user;
    private Status status;

    public OrderSearchFilter() {
        super(Order.class);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void buildPredicates(Root<Order> root) {
        addEqual(root.get("user"), getUser());
        addEqual(root.get("status"), getStatus());
    }
}
