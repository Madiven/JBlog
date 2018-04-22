package com.jblog.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CommentVO {
    /**
     * 当前评论
     */
    private Comment comment;
    /**
     * 评论楼层
     */
    private Integer floor;
    /**
     * 子评论
     */
    private List<CommentVO> comments;

    public CommentVO() {

    }

    public CommentVO(Comment comment) {
        this.comment = comment;
    }
}
