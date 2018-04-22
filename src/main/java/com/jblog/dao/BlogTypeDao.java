package com.jblog.dao;

import com.jblog.pojo.Blog;
import com.jblog.pojo.BlogType;
import com.jblog.pojo.BlogTypeVO;

import java.util.List;
import java.util.Map;

/**
 * @author devin
 */
public interface BlogTypeDao {

    /**
     * 获取带有博客分类下博客数量的分类信息
     */
    List<BlogType> getBlogType4Init();

    /**
     * 获取博客分类的信息
     */
    List<BlogType> listBlogType();

    /**
     * 根据id获取分类信息
     */
    BlogType findById(Integer id);

    Integer getCountByTypeName(String typeName);

    /**
     * 添加博客分类
     */
    Integer addBlogType(BlogType blogType);

    /**
     * 删除博客分类
     */
    Integer deleteBlogType(Integer id);

    /**
     * 更新博客分类
     */
    Integer updateBlogType(BlogType blogType);

    List<BlogTypeVO> getCount();
}
