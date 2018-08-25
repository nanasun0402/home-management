package com.caring.dao.repository;

import com.caring.dao.model.Goods;
import com.caring.dao.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    public List<ShoppingCart> findByGoods(Goods goods);

}
