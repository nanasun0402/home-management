package com.caring.wxrs.rest.response;

/**
 *
 * @author james
 */
public class FileUploadMaxSizeResponse {

    private Long size;

    public FileUploadMaxSizeResponse(Long size) {
        this.size = size;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

}
