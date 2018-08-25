package com.caring.dao.repository;

import com.caring.dao.model.CollectGoods;
import com.caring.dao.model.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectGoodsRepository extends JpaRepository<CollectGoods, Long> {
    public CollectGoods findByGoods_IdAndMember_Id(Long goodsId, Long memberId);
}
