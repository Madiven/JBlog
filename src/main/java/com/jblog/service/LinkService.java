package com.jblog.service;

import com.github.pagehelper.PageInfo;
import com.jblog.pojo.Link;

public interface LinkService {

    PageInfo<Link> listLinkData(Integer page, Integer rows);

    Integer addLink(Link link);

    Integer deleteLink(Integer[] ids);

    Integer updateLink(Link link);
}
