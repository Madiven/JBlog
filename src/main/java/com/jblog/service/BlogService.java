package com.jblog.service;

import com.github.pagehelper.PageInfo;
import com.jblog.pojo.Blog;

import java.util.List;

public interface BlogService {

    PageInfo<Blog> listBlog(Integer page, Integer rows);

    PageInfo<Blog> listBlog(Integer page, Integer rows, Integer navigatePages);

    Blog getBlogById(Integer id);

    Integer save(Blog blog);

    Integer update(Blog blog);

    Integer deleteBlog(Integer[] ids);

    PageInfo<Blog> searchBlogByTitle(String title, Integer page, Integer rows);

    List<Blog> getRecommendArticles();

    List<Blog> getArticlesByType(Integer id);

    public Blog readBlog(Integer id);

    List<Blog> getArticlesByDate(String strReleaseDate);

    Blog getPrevBlog(Integer id);

    Blog getNextBlog(Integer id);

}
