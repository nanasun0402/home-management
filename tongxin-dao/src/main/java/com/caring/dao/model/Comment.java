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
@Table(name = "comment")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Comment extends  BaseEntity {
    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 10000, sequenceName = "comment_id_seq", name = "comment_id_seq")
    @GeneratedValue(generator = "comment_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @OrderBy("id")
    @JoinTable(
            name = "comment_image",
            joinColumns = {
                    @JoinColumn(name = "comment_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "image_id", referencedColumnName = "id")})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Image> images;

    @OneToOne
    private Order order;

    @OneToOne
    private Member member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
