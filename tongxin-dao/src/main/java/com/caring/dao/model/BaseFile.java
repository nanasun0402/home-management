package com.caring.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.InputStream;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 *
 * @author james
 */
@MappedSuperclass
public class BaseFile extends BaseEntity {

    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 10000, sequenceName = "base_file_id_seq", name = "base_file_id_seq")
    @GeneratedValue(generator = "base_file_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "file_path", length = 2048)
    private String filePath;

    @Column(name = "origin_file_path", length = 2048)
    private String originFilePath;

    @Column(name = "file_url", length = 2048)
    private String fileUrl;

    @Column(name = "type")
    private String type;

    @Column(name = "file_purpose")
    private String filePurpose;

    @Column(name = "owner_id")
    private Long ownerId;

    @Transient
    @JsonIgnore
    private InputStream dataInput;

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOriginFilePath() {
        return originFilePath;
    }

    public void setOriginFilePath(String originFilePath) {
        this.originFilePath = originFilePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePurpose() {
        return filePurpose;
    }

    public void setFilePurpose(String filePurpose) {
        this.filePurpose = filePurpose;
    }

    public InputStream getDataInput() {
        return dataInput;
    }

    public void setDataInput(InputStream dataInput) {
        this.dataInput = dataInput;
    }

}
