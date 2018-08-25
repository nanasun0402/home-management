package com.caring.dao.repository;

import com.caring.dao.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public Comment findByOrder_IdAndMember_Id(Long orderId, Long memberId);
}
