package com.jblog.dao;

import com.jblog.pojo.Blog;

import java.util.List;
import java.util.Map;

/**
 * @author devin
 */
//@Repository
public interface BlogDao {

    /**
     * 查询所有文章
     */
    List<Blog> listBlog();
    /**
     * 根据id检索文章
     */
    Blog findById(Integer id);
    /**
     * 更新文章
     */
    Integer update(Blog blog);
    /**
     * 新增文章
     */
    Integer addBlog(Blog blog);
    /**
     * 删除文章
     */
    Integer deleteBlog(Integer[] ids);

    Integer getCountByBlogType(Integer typdId);

    List<Blog> searchBlogByTitle(String title);

    Map<String,Integer> getCount();

    List<Blog> getCurrent();

    Map<String,Integer> getRecord();

    List<Blog> getTopReadCount();

    List<Blog> getRecommendArticles();

    List<Blog> getArticlesByType(Integer id);

    List<Blog> getArticlesByDate(String strReleaseDate);

    Blog getPrevBlog(Integer id);

    Blog getNextBlog(Integer id);

    Integer updateReadCount(Integer id);

    Integer increaseCommentCount(Integer id);

    Integer decreaseCommentCount(Integer id);
}
