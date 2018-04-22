package com.jblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jblog.dao.BlogDao;
import com.jblog.dao.CommentDao;
import com.jblog.pojo.Blog;
import com.jblog.pojo.Comment;
import com.jblog.pojo.CommentVO;
import com.jblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BlogDao blogDao;

    @Override
    public PageInfo<Comment> listComment(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Comment> items = commentDao.getAll();
        return new PageInfo<>(items);
    }

    @Override
    public PageInfo<Comment> listComment2Review(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Comment> items = commentDao.getAll2Review();
        return new PageInfo<>(items);
    }

    @Override
    public Integer update(Integer state, Integer[] idList) {
        if(state == 1) {
            for(Integer id : idList) {
                blogDao.increaseCommentCount(id);
            }
        }
        return commentDao.updateState(state, idList);
    }

    @Override
    public Integer deleteComment(Integer[] ids) {
        for(Integer id : ids) {
            blogDao.decreaseCommentCount(id);
        }
        return commentDao.deleteComment(ids);
    }

    @Override
    public Integer addComment(Comment comment) {
        if(comment.getState() == 1) {
            blogDao.increaseCommentCount(comment.getId());
        }
        return commentDao.addComment(comment);
    }

    @Override
    public  List<CommentVO> getByBlogId(Integer id) {
        List<Comment> comments = commentDao.getByBlogId(id);
        List<CommentVO> commentVOs = new LinkedList<>();
        int floor = 1;
        for (Comment item : comments) {
            if(item.getParentId()==null) {
                CommentVO commentVO = new CommentVO(item);
                commentVO.setFloor(floor);
                commentVOs.add(commentVO);
                floor++;
            }
        }
        buildCommentTree(comments, commentVOs);
        return commentVOs;
    }

    private void buildCommentTree(List<Comment> comments, List<CommentVO> commentVOs) {
        if(commentVOs != null && commentVOs.size() > 0) {
            for(CommentVO commentVO : commentVOs ) {
                List<CommentVO> commentVOList = new LinkedList<>();
                for (Comment comment : comments) {
                    if(commentVO.getComment().getId().equals(comment.getParentId())) {
                        CommentVO vo = new CommentVO(comment);
                        vo.setFloor(commentVO.getFloor());
                        commentVOList.add(vo);
                    }
                }
                commentVO.setComments(commentVOList);
                buildCommentTree(comments, commentVO.getComments());
            }
        }

    }
}
