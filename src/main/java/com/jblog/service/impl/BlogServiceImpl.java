package com.jblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jblog.common.exception.BusinessException.BlogNotFoundException;
import com.jblog.dao.BlogDao;
import com.jblog.dao.CommentDao;
import com.jblog.pojo.Blog;
import com.jblog.service.BlogService;
import com.jblog.pojo.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private CommentDao commentDao;

    @Override
    public PageInfo<Blog> listBlog(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Blog> items = blogDao.listBlog();
        return new PageInfo<Blog>(items);
    }

    @Override
    public PageInfo<Blog> listBlog(Integer page, Integer rows, Integer navigatePages) {
        PageHelper.startPage(page, rows);
        List<Blog> items = blogDao.listBlog();
        return new PageInfo<Blog>(items, navigatePages);
    }

    @Override
    public Blog getBlogById(Integer id){
        Blog blog = blogDao.findById(id);
        if(blog == null) {
            throw new BlogNotFoundException();
        }
        return blog;
    }

    @Override
    public Integer save(Blog blog) {
        return blogDao.addBlog(blog);
    }

    @Override
    public Integer update(Blog blog) {
        return blogDao.update(blog);
    }

    @Override
    public Integer deleteBlog(Integer[] ids) {
        commentDao.deleteCommentByBlogId(ids);
        return  blogDao.deleteBlog(ids);
    }

    @Override
    public PageInfo<Blog> searchBlogByTitle(String title,Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Blog> items = blogDao.searchBlogByTitle(title);
        return new PageInfo<Blog>(items);
    }

    @Override
    public List<Blog> getRecommendArticles() {
        return blogDao.getRecommendArticles();
    }

    @Override
    public List<Blog> getArticlesByType(Integer id) {
        return blogDao.getArticlesByType(id);
    }

    @Override
    public Blog readBlog(Integer id) {
        blogDao.updateReadCount(id);
        return blogDao.findById(id);
    }

    @Override
    public List<Blog> getArticlesByDate(String strReleaseDate) {
        return blogDao.getArticlesByDate(strReleaseDate);
    }

    @Override
    public Blog getPrevBlog(Integer id) {
        return blogDao.getPrevBlog(id);
    }

    @Override
    public Blog getNextBlog(Integer id) {
        return blogDao.getNextBlog(id);
    }
}
