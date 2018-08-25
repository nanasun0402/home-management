package com.caring.wxrs.rest;

import com.caring.dao.model.Goods;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.GoodsFilter;
import com.caring.dao.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/goods")
public class GoodsRestController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<Goods> findGoods(@RequestBody(required = false) PageParam<GoodsFilter> pageParam) {
        return goodsService.findGoods(pageParam);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Goods saveGoods(@RequestBody Goods goods) {
        return goodsService.saveGoods(goods);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Goods findGoodsById(@PathVariable Long id) {
        return goodsService.findGoodsById(id);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteGoodsById(@PathVariable Long id) {
        goodsService.deleteGoods(id);
    }
}
