package com.caring.dao.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "goods")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Goods extends  BaseEntity {
    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 10000, sequenceName = "goods_id_seq", name = "goods_id_seq")
    @GeneratedValue(generator = "goods_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name; //标题

    @Column(name = "type")
    private String type; //类别

    @Column(name = "label")
    private String label; //标签

    @Column(name = "description")
    private String description; //描述

    @Column(name = "start_price")
    private String startPrice; //正常价

    @Column(name = "sale_price")
    private String salePrice; //促销价

    @Column(name = "material")
    private String material; //材质

    @Column(name = "goods_count")
    private Long goodsCount; //库存

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @OrderBy("id")
    @JoinTable(
            name = "goods_size",
            joinColumns = {
                    @JoinColumn(name = "goods_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "size_id", referencedColumnName = "id")})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Size> sizes;      //尺寸

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @OrderBy("id")
    @JoinTable(
            name = "goods_color",
            joinColumns = {
                    @JoinColumn(name = "goods_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "color_id", referencedColumnName = "id")})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Color> colors;      //颜色

    @OneToMany(fetch = FetchType.EAGER)
    @OrderBy("id")
    @JoinTable(
            name = "goods_image",
            joinColumns = {
                    @JoinColumn(name = "goods_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "image_id", referencedColumnName = "id")})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Image> images;      //商品图片

    @OneToMany(fetch = FetchType.EAGER)
    @OrderBy("id")
    @JoinTable(
            name = "goods_detail_image",
            joinColumns = {
                    @JoinColumn(name = "goods_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "image_id", referencedColumnName = "id")})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Image> detailImages;      //商品详情图片

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Long getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Long goodsCount) {
        this.goodsCount = goodsCount;
    }

    public List<Image> getDetailImages() {
        return detailImages;
    }

    public void setDetailImages(List<Image> detailImages) {
        this.detailImages = detailImages;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }
}
