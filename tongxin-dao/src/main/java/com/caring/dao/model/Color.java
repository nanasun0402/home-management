package com.caring.dao.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name = "color")
public class Color {
    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 10, sequenceName = "color_id_seq", name = "color_id_seq")
    @GeneratedValue(generator = "color_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name; //颜色名称

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

}
