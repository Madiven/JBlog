package com.jblog.dao;

import com.jblog.pojo.Link;

import java.util.List;

//@Repository
public interface LinkDao {
    // 获取友情链接
    List<Link> getLinkData();

    // 分页获取友情链接
    List<Link> listLinkData();

    // 添加友情链接
    Integer addLink(Link link);

    // 更新友情链接
    Integer updateLink(Link link);

    // 删除友情链接
    Integer deleteLink(Integer[] ids);

}
