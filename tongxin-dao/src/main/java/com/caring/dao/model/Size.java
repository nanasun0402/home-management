package com.caring.dao.model;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "size")
public class Size {
    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 10, sequenceName = "size_id_seq", name = "size_id_seq")
    @GeneratedValue(generator = "size_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name; //尺寸名称

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
