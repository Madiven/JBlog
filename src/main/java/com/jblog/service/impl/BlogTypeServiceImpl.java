package com.jblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jblog.dao.BlogDao;
import com.jblog.dao.BlogTypeDao;
import com.jblog.pojo.BlogType;
import com.jblog.service.BlogTypeService;
import com.jblog.service.BlogTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService {

    @Autowired
    private BlogTypeDao blogTypeDao;

    @Autowired
    private BlogDao blogDao;



    @Override
    public Integer save(BlogType blogType) {
        Integer count = blogTypeDao.getCountByTypeName(blogType.getTypeName());
        if(count == 0) {
            return blogTypeDao.addBlogType(blogType);
        } else {
            return 0;
        }
    }

    @Override
    public String deleteBlogType(List<BlogType> blogTypes) {

        List<String> typeNames = new LinkedList<>();
        for (BlogType blogType : blogTypes) {
            if (blogDao.getCountByBlogType(blogType.getId()) > 0) { //说明该类别中有博客
                typeNames.add(blogType.getTypeName());
            } else {
                blogTypeDao.deleteBlogType(blogType.getId());
            }
        }
        return StringUtils.join(typeNames,',');
    }

    @Override
    public Integer update(BlogType blogType) {
        return blogTypeDao.updateBlogType(blogType);
    }

    @Override
    public PageInfo<BlogType> listBlogType(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<BlogType> items = blogTypeDao.listBlogType();
        return new PageInfo<>(items);
    }

    @Override
    public List<BlogType> getAll() {
        return blogTypeDao.listBlogType();
    }

    @Override
    public BlogType findById(Integer id) {
        return blogTypeDao.findById(id);
    }
}
