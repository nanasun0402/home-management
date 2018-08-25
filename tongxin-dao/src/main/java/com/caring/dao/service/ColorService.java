package com.caring.dao.service;

import com.caring.dao.model.Color;
import com.caring.dao.model.Size;
import com.caring.dao.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorService extends BaseService {
    @Autowired
    private ColorRepository colorRepository;

    public Color saveColor(Color color) {
        return colorRepository.save(color);
    }

    public void deleteColor(Long id) {
        colorRepository.delete(id);
    }

    public Color findColorById(Long id) {
        return colorRepository.findOne(id);
    }
}
