package com.jblog.service;

import com.github.pagehelper.PageInfo;
import com.jblog.pojo.BlogType;

import java.util.List;

public interface BlogTypeService{

    Integer save(BlogType blogType);

    String deleteBlogType(List<BlogType> blogTypes);

    Integer update(BlogType blogType);

    PageInfo<BlogType> listBlogType(Integer page, Integer rows);

    List<BlogType> getAll();

    BlogType findById(Integer id);
}
