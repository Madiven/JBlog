package com.jblog.service;

import com.github.pagehelper.PageInfo;
import com.jblog.pojo.Comment;
import com.jblog.pojo.CommentVO;

import java.util.List;

public interface CommentService {

    PageInfo<Comment> listComment(Integer page, Integer rows);

    PageInfo<Comment> listComment2Review(Integer page, Integer rows);

    Integer update(Integer state, Integer[] idList);

    Integer deleteComment(Integer[] ids);

    List<CommentVO> getByBlogId(Integer id);

    Integer addComment(Comment comment);

}
