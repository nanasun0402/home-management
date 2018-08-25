//package com.caring.dao.model.system;
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import java.io.Serializable;
//import java.util.Date;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.PrePersist;
//import javax.persistence.PreUpdate;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
///**
// *
// * @author james
// */
//@SuppressWarnings("serial")
//@Entity
//@Table(name = "event_queue")
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
//public class Event implements Serializable {
//
//    @Id
//    @SequenceGenerator(allocationSize = 1, initialValue = 10000, sequenceName = "task_queue_id_seq", name = "task_queue_id_seq")
//    @GeneratedValue(generator = "task_queue_id_seq", strategy = GenerationType.SEQUENCE)
//    private Long id;
//
//    @Column(name = "status")
//    private String status;
//
//    @Column(name = "type")
//    private String type;
//
//    @Column(name = "message", length = 1024)
//    private String message;
//
//    @Column(name = "detail", length = 20480)
//    private String detail;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created", nullable = false, updatable = false)
//    private Date created;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "updated", nullable = false)
//    private Date updated;
//
//    @PrePersist
//    protected void onCreate() {
//        updated = created = new Date();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updated = new Date();
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getDetail() {
//        return detail;
//    }
//
//    public void setDetail(String detail) {
//        this.detail = detail;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Date getCreated() {
//        return created;
//    }
//
//    public void setCreated(Date created) {
//        this.created = created;
//    }
//
//    public Date getUpdated() {
//        return updated;
//    }
//
//    public void setUpdated(Date updated) {
//        this.updated = updated;
//    }
//
//}
