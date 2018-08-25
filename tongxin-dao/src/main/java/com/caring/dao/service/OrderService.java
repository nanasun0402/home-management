package com.caring.dao.service;

import com.caring.dao.model.Order;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.OrderFilter;
import com.caring.dao.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OrderService extends BaseService {
    @Autowired
    private OrderRepository orderRepository;

    public Page<Order> findOrders(PageParam<OrderFilter> pageParam) {
        StringBuilder SQL = new StringBuilder("SELECT o FROM Order o");
        StringBuilder SQLcount = new StringBuilder("SELECT COUNT(1) FROM Order o");
        StringBuilder OrderBy = new StringBuilder("ORDER BY o.created DESC");
        return executePageQuery(pageParam, SQL, SQLcount, OrderBy);
    }

    public Order findOrderById(Long id) {
        return orderRepository.findOne(id);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }


    public void deleteOrder(Long id) {
        orderRepository.delete(id);
    }
}
