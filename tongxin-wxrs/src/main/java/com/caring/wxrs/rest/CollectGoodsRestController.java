package com.caring.wxrs.rest;

import com.caring.dao.model.CollectGoods;
import com.caring.dao.model.Goods;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.CollectGoodsFilter;
import com.caring.dao.service.CollectGoodsService;
import com.caring.wxrs.JsonProperties;
import com.caring.wxrs.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/collectGoods")
public class CollectGoodsRestController {
    @Autowired
    private CollectGoodsService collectGoodsService;

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JsonProperties({
            @JsonProperty(
                    affectClass = Goods.class,
                    includeProperties = {"id", "name", "headImage", "salePrice", "startPrice", "sizes", "colors", "images"}
            )
    })
    public Page<CollectGoods> findCollectGoods(@RequestBody(required = false) PageParam<CollectGoodsFilter> pageParam) {
        return collectGoodsService.findCollectGoods(pageParam);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CollectGoods saveCollectGoods(@RequestBody CollectGoods collectGoods) {
        return collectGoodsService.saveCollectGoods(collectGoods);
    }

//    @RequestMapping(value = "/{id}",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public CollectGoods findCollectGoodsById(@PathVariable Long id) {
//        return collectGoodsService.findCollectGoodsById(id);
//    }

    @RequestMapping(value = "/one",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CollectGoods findByGoodsAndMember(@RequestParam("goodsId") Long goodsId, @RequestParam("memberId") Long memberId) {
        return collectGoodsService.findByGoods_IdAndMember_Id(goodsId, memberId);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteCollectGoodsById(@PathVariable Long id) {

        collectGoodsService.deleteCollectGoods(id);
    }
}
