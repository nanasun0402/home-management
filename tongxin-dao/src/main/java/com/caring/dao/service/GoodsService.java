package com.caring.dao.service;

import com.caring.dao.model.Goods;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.GoodsFilter;
import com.caring.dao.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsService extends BaseService {
    @Autowired
    private GoodsRepository goodsRepository;

    public Page<Goods> findGoods(PageParam<GoodsFilter> pageParam) {
        StringBuilder SQL = new StringBuilder("SELECT g FROM Goods g");
        StringBuilder SQLcount = new StringBuilder("SELECT COUNT(1) FROM Goods g");
        StringBuilder OrderBy = new StringBuilder("ORDER BY g.created DESC");
        return executePageQuery(pageParam, SQL, SQLcount, OrderBy);
    }

    public Goods findGoodsById(Long id) {
        return goodsRepository.findOne(id);
    }

    public Goods saveGoods(Goods goods) {
        return goodsRepository.save(goods);
    }

    public void deleteGoods(Long id) {
        goodsRepository.delete(id);
    }
}
