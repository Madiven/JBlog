package com.jblog.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author devin
 * 博客
 */
@Data
public class Blog {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 博客标题
     */
    private String title;
    /**
     * 博客摘要
     */
    private String summary;
    /**
     * 发表时期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date releaseDate;
    /**
     * 阅读数
     */
    private Integer readCount;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 博客正文，用于前台显示
     */
    private String content;
    /**
     * 不带标签的博客内容，用于Lucene索引，表中没有该字段。
     */
    private String contentNoTag;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 博客分类
     */
    private BlogType blogType;

    /**
     * 博客类型：原创、转载、翻译
     */
    private Integer type;

    /**
     * 是否显示博客
     */
    private Boolean show;
    /**
     * 是否可以评论
     */
    private Boolean reply;
    /**
     * 是否推荐
     */
    private Boolean commend;
}
