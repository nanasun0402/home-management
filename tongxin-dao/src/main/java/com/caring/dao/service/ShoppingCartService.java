package com.caring.dao.service;

import com.caring.dao.model.Goods;
import com.caring.dao.model.ShoppingCart;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.ShoppingCartFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.caring.dao.repository.ShoppingCartRepository;

import java.util.List;
import java.util.Objects;

@Service
public class ShoppingCartService extends BaseService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public Page<ShoppingCart> findShoppingCarts(PageParam<ShoppingCartFilter> pageParam) {
        StringBuilder SQL = new StringBuilder("SELECT sc FROM ShoppingCart sc");
        StringBuilder SQLcount = new StringBuilder("SELECT COUNT(1) FROM ShoppingCart sc");
        StringBuilder OrderBy = new StringBuilder("ORDER BY sc.created DESC");
        return executePageQuery(pageParam, SQL, SQLcount, OrderBy);
    }

    public ShoppingCart findShoppingCartById(Long id) {
        return shoppingCartRepository.findOne(id);
    }

    public ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
        if (Objects.nonNull(shoppingCart.getGoods()) && shoppingCart.getGoods().getId() != null) {
            Goods goods=new Goods();
            goods.setId(shoppingCart.getGoods().getId());
            List<ShoppingCart> shoppingCartList =
                    shoppingCartRepository.findByGoods(goods);
            for (int i = 0; i < shoppingCartList.size(); i++) {
                //商品颜色不为空，且颜色与购物车中颜色相同
                if (StringUtils.isNotEmpty(shoppingCart.getColor())
                        && StringUtils.equals(shoppingCart.getColor(), shoppingCartList.get(i).getColor())) {
                    //商品尺寸不为空，与购物车中相同
                    if (StringUtils.isNotEmpty(shoppingCart.getSize())
                            && StringUtils.equals(shoppingCart.getSize(), shoppingCartList.get(i).getSize())
                            ) {
                        shoppingCartRepository.save(goodsCount(shoppingCartList.get(i), shoppingCart));
                        return shoppingCartList.get(i);
                    } else {
                        //商品尺寸为空
                        if(StringUtils.isEmpty(shoppingCart.getSize())) {
                            shoppingCartRepository.save(goodsCount(shoppingCartList.get(i), shoppingCart));
                            return shoppingCartList.get(i);
                        }else{ //商品尺寸不为空，与购物车中不相同
                            if(i==shoppingCartList.size()-1) {
                                shoppingCartRepository.save(shoppingCart);
                                return shoppingCart;
                            }
                        }
                    }
                } else {
                    //颜色为空  或者颜色不为空，与购物车相等
                    if(StringUtils.isEmpty(shoppingCart.getColor()) ||(StringUtils.isNotEmpty(shoppingCart.getColor())
                            && StringUtils.equals(shoppingCart.getColor(),shoppingCartList.get(i).getColor()))){
                        //尺寸不为空，与购物车相同 或者尺寸為空
                        if((StringUtils.isNotEmpty((shoppingCart.getSize()))
                                && StringUtils.equals(shoppingCart.getSize(), shoppingCartList.get(i).getSize()) || StringUtils.isEmpty(shoppingCart.getSize()))){
                            shoppingCartRepository.save(goodsCount(shoppingCartList.get(i), shoppingCart));
                        return shoppingCartList.get(i);
                        }else{
                            //尺寸不為空，与购物车不相同
                            shoppingCartRepository.save(shoppingCart);
                                return shoppingCart;
                        }
                    }
                }
            }
            shoppingCartRepository.save(shoppingCart);
            return shoppingCart;
        }
        return null;
    }

    public void deleteShoppingCart(Long id) {
        shoppingCartRepository.delete(id);
    }

    private ShoppingCart goodsCount(ShoppingCart paramCart,ShoppingCart requestCart){
        paramCart.setCount(paramCart.getCount()+requestCart.getCount());
        return  paramCart;
    }
}
