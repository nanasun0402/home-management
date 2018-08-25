package com.caring.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

/**
 *
 * @author james
 */
@Component
@PropertySources({
    @PropertySource("classpath:file.storage.local.properties")
})
public class FileLocalStorageConfig {

    @Value("${file.local.storage.root}")
    private String fileStorageRoot;

    @Value("${file.max.size.mb}")
    private long fileMaxSizeMb;

    public String getFileStorageRoot() {
        return fileStorageRoot;
    }

    public void setFileStorageRoot(String fileStorageRoot) {
        this.fileStorageRoot = fileStorageRoot;
    }

    public long getFileMaxSizeMb() {
        return fileMaxSizeMb;
    }

    public void setFileMaxSizeMb(long fileMaxSizeMb) {
        this.fileMaxSizeMb = fileMaxSizeMb;
    }

}
