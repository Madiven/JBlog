package com.jblog.service.impl;

import com.jblog.dao.BloggerDao;
import com.jblog.dao.CommentDao;
import com.jblog.pojo.Blogger;
import com.jblog.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService{

    @Autowired
    private BloggerDao bloggerDao;

    @Override
    public Blogger getByUserName(String userName) {
        return bloggerDao.getByUserName(userName);
    }

    @Override
    public Blogger getBlogger() {
        Blogger blogger = bloggerDao.getBlogger();
        blogger.setPassword(null);
        return blogger;
    }

    @Override
    public Integer updateBlogger(Blogger blogger) {
        return bloggerDao.updateBlogger(blogger);
    }

}
