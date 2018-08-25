package com.caring.dao.repository;

import com.caring.dao.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author james
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
