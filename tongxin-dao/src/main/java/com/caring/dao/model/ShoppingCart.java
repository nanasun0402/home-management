package com.caring.dao.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@Table(name = "shopping_cart")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class ShoppingCart extends  BaseEntity {
    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 10000, sequenceName = "shoppingCart_id_seq", name = "shoppingCart_id_seq")
    @GeneratedValue(generator = "shoppingCart_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @OneToOne
    private Member member;

    @OneToOne
    private Goods goods;

    @Column(name = "count")
    private Long count;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

//    public ShoppingCart() {
//    }
//    public ShoppingCart(Long id, Date created, Date updated,
//                        Long goodsId, String goodsName){
//        super(created, updated);
//        this.id = id;
//        this.goods = new Goods();
//        this.goods.setId(goodsId);
//        this.goods.setName(goodsName);
////        this.group.setCreatorDoctorId(creatorDoctorId);
////        this.group.setCreatorDoctorName(creatorDoctorName);
////        this.customer = new Customer();
////        this.customer.setId(customerId);
////        this.customer.setName(customerName);
////        this.customer.setMobile(customerMobile);
////        this.customer.setChild(new Child());
////        this.customer.getChild().setId(childId);
////        this.customer.getChild().setName(childName);
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
