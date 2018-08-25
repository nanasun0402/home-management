package com.caring.wxrs.rest;

import com.caring.dao.model.Comment;
import com.caring.dao.model.Goods;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.CommentFilter;
import com.caring.dao.repository.CommentRepository;
import com.caring.dao.service.CommentService;
import com.caring.wxrs.JsonProperties;
import com.caring.wxrs.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentRestController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JsonProperties({
            @JsonProperty(
                    affectClass = Goods.class,
                    includeProperties = {"id", "name", "headImage", "salePrice", "startPrice", "sizes", "colors", "images"}
            )
    })
    public Page<Comment> findComments(@RequestBody(required = false) PageParam<CommentFilter> pageParam) {
        return commentService.findComments(pageParam);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Comment saveComment(@RequestBody Comment comment) {

        return commentService.saveComment(comment);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Comment findByOrderIdAndMemberId(@RequestParam("orderId") Long orderId,@RequestParam("memberId") Long memberId) {
        return commentRepository.findByOrder_IdAndMember_Id(orderId, memberId);
    }
}
