package com.jblog.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    /**
     * id
     */
    private Integer id;

    /**
     * 评论者IP
     */
    private Integer userIp;

    /**
     * 评论者名称
     */
    private String userName;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date commentDate;
    /**
     * 审核状态，0待审核 1审核通过 2未通过
     */
    private Integer state;

    /**
     * 父评论ID
     */
    private Integer parentId;

    /**
     * 关联的博客
     */
    private Blog blog;
}
