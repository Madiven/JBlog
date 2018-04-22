package com.jblog.service;

import com.jblog.pojo.Blogger;

public interface BloggerService {

    Blogger getByUserName(String userName);

    Blogger getBlogger();

    Integer updateBlogger(Blogger blogger);
}
