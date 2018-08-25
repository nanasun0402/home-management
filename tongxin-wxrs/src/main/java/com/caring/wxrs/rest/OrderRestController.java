package com.caring.wxrs.rest;

import com.caring.dao.model.Goods;
import com.caring.dao.model.Order;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.OrderFilter;
import com.caring.dao.service.OrderService;
import com.caring.wxrs.JsonProperties;
import com.caring.wxrs.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderRestController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JsonProperties({
            @JsonProperty(
                    affectClass = Goods.class,
                    includeProperties = {"id", "name", "headImage", "salePrice", "startPrice", "sizes", "colors", "images"}
            )
    })
    public Page<Order> findOrders(@RequestBody(required = false) PageParam<OrderFilter> pageParam) {
        return orderService.findOrders(pageParam);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order saveOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
