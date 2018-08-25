package com.caring.dao.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name = "my_order")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Order extends BaseEntity {
    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 10000, sequenceName = "order_id_seq", name = "order_id_seq")
    @GeneratedValue(generator = "order_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    private Member member;

    @OneToOne
    private Goods goods;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "count")
    private Long count;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

    @Column(name = "is_delivery")
    private String delivery;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
