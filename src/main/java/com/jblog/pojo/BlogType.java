package com.jblog.pojo;

import lombok.Data;

/**
 * @author devin
 * 博客类型
 */
@Data
public class BlogType {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 分类名称
     */
    private String typeName;
    /**
     * 分类排序
     */
    private String orderNum;

    /**
     * 是否显示该分类，及以下所有文章
     */
    private Boolean show;
}
