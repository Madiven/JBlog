package com.jblog.dao;

import com.jblog.pojo.Blogger;

/**
 * @author devin
 */
//@Repository
public interface BloggerDao {
    /**
     * 根据登陆名获取博主信息
     * @param userName 登录名
     * @return 博主对象
     */
    Blogger getByUserName(String userName);

    /**
     * 获取博主信息(因为是个人博客，所以只有一条数据)
     * @return 博主对象
     */
    Blogger getBlogger();

    /**
     * 更新博主信息
     * @param blogger 需要更新的信息
     * @return 受影响记录数
     */
    Integer updateBlogger(Blogger blogger);

    /**
     * 获取博主信息
     * @return 博主对象
     */
    Blogger getBloggerMsg();
}
