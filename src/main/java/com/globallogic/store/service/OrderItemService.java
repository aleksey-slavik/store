package com.globallogic.store.service;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.exception.NoContentException;
import com.globallogic.store.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private GenericDao<OrderItem> orderItemDao;

    public OrderItemService(GenericDao<OrderItem> orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    public OrderItem getByIds(long orderId, long productId) throws NotFoundException {
        SearchCriteria criteria = new SearchCriteria()
                .criteria("order", orderId)
                .criteria("product", productId);

        OrderItem item = orderItemDao.getEntity(criteria);

        if (item == null) {
            throw new NotFoundException("Order item with orderId=" + orderId + " and productId=" + productId + " not found!");
        } else {
            return item;
        }
    }

    public List<OrderItem> getList(SearchCriteria criteria) throws NoContentException {
        List<OrderItem> items = orderItemDao.getEntityList(criteria);

        if (items == null || items.isEmpty()) {
            throw new NoContentException("Order items with given criteria not found!");
        } else {
            return items;
        }
    }
}
