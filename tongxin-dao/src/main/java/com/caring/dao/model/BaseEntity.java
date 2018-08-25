package com.caring.dao.model;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author james
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    private Date created;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    private Date updated;

//    @Column(name = "sequence_number")
//    private Long sequence = 1L;


    public BaseEntity() {
    }

    public BaseEntity(Date created, Date updated) {
        this.created = created;
        this.updated = updated;
    }

//    public BaseEntity(Date created, Date updated) {
//        this.created = created;
//        this.updated = updated;
////        this.sequence = sequence;
//    }

    @PrePersist
    protected void onCreate() {
        updated = created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }


    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

//    public Long getSequence() {
//        return sequence;
//    }
//
//    public void setSequence(Long sequence) {
//        this.sequence = sequence;
//    }

}
