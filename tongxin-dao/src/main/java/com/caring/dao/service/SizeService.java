package com.caring.dao.service;

import com.caring.dao.model.Size;
import com.caring.dao.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SizeService extends BaseService {
    @Autowired
    private SizeRepository sizeRepository;

    public Size saveSize(Size size) {
        return sizeRepository.save(size);
    }

    public void deleteSize(Long id) {
        sizeRepository.delete(id);
    }

    public Size findSizeById(Long id) {
        return sizeRepository.findOne(id);
    }
}
