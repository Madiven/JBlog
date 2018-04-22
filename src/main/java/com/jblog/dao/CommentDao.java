package com.jblog.dao;

import com.jblog.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDao {

    List<Comment> getAll();

    List<Comment> getAll2Review();

    List<Comment> getByBlogId(Integer id);

    Integer updateState(@Param("state") Integer state, @Param("idList") Integer[] idList);

    Integer addComment(Comment comment);

    Integer deleteComment(Integer[] ids);

    Integer deleteCommentByBlogId(Integer[] ids);

}
