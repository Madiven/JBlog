package com.jblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jblog.dao.LinkDao;
import com.jblog.pojo.Link;
import com.jblog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("linkService")
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDao linkDao;

    @Override
    public PageInfo<Link> listLinkData(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Link> items = linkDao.listLinkData();
        return new PageInfo<>(items);
    }

    @Override
    public Integer addLink(Link link) {
        return linkDao.addLink(link);
    }

    @Override
    public Integer deleteLink(Integer[] ids) {
        return linkDao.deleteLink(ids);
    }

    @Override
    public Integer updateLink(Link link) {
        return linkDao.updateLink(link);
    }
}
