package com.caring.dao.service;

import com.caring.dao.model.Comment;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.CommentFilter;
import com.caring.dao.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends BaseService {
    @Autowired
    private CommentRepository commentRepository;

    public Page<Comment> findComments(PageParam<CommentFilter> pageParam) {
        StringBuilder SQL = new StringBuilder("SELECT com FROM Comment com");
        StringBuilder SQLcount = new StringBuilder("SELECT COUNT(1) FROM Comment com");
        StringBuilder OrderBy = new StringBuilder("ORDER BY com.created DESC");
        return executePageQuery(pageParam, SQL, SQLcount, OrderBy);
    }

    public Comment findCommenById(Long id) {
        return commentRepository.findOne(id);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }

    public Comment findByOrder_IdAndMember_Id(Long orderId, Long memberId) {
        return commentRepository.findByOrder_IdAndMember_Id(orderId, memberId);
    }
}
