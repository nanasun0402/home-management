package com.caring.wxrs.rest;

import com.caring.dao.model.Goods;
import com.caring.dao.model.ShoppingCart;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.ShoppingCartFilter;
import com.caring.dao.service.ShoppingCartService;
import com.caring.wxrs.JsonProperties;
import com.caring.wxrs.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shoppingCart")
public class ShoppingCartRestController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JsonProperties({
            @JsonProperty(
                    affectClass = Goods.class,
                    includeProperties = {"id", "name", "headImage", "salePrice", "startPrice", "sizes", "colors", "images"}
            )
           })
    public Page<ShoppingCart> findShoppingCarts(@RequestBody(required = false) PageParam<ShoppingCartFilter> pageParam) {
        return shoppingCartService.findShoppingCarts(pageParam);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ShoppingCart saveShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.saveShoppingCart(shoppingCart);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ShoppingCart findShoppingCartById(@PathVariable Long id) {
        return shoppingCartService.findShoppingCartById(id);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteShoppingCartById(@PathVariable Long id) {

        shoppingCartService.deleteShoppingCart(id);
    }
}
