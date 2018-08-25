package com.caring.dao.service;

import com.caring.dao.model.CollectGoods;
import com.caring.dao.model.Goods;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.CollectGoodsFilter;
import com.caring.dao.repository.CollectGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectGoodsService extends BaseService {
    @Autowired
    private CollectGoodsRepository collectGoodsRepository;

    public Page<CollectGoods> findCollectGoods(PageParam<CollectGoodsFilter> pageParam) {
        StringBuilder SQL = new StringBuilder("SELECT cg FROM CollectGoods cg");
        StringBuilder SQLcount = new StringBuilder("SELECT COUNT(1) FROM CollectGoods cg");
        StringBuilder OrderBy = new StringBuilder("ORDER BY cg.created DESC");
        return executePageQuery(pageParam, SQL, SQLcount, OrderBy);
    }

    public CollectGoods findCollectGoodsById(Long id) {
        return collectGoodsRepository.findOne(id);
    }
    public CollectGoods findByGoods_IdAndMember_Id(Long goodsId, Long memberId) {
        return collectGoodsRepository.findByGoods_IdAndMember_Id(goodsId, memberId);
    }

    public CollectGoods saveCollectGoods(CollectGoods collectGoods) {
        return collectGoodsRepository.save(collectGoods);
    }

    public void deleteCollectGoods(Long id) {
        collectGoodsRepository.delete(id);
    }
}
