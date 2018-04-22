package com.jblog.pojo;

import lombok.Data;

/**
 * @author devin
 * 博主
 */
@Data
public class Blogger {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 登录名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 博主信息
     */
    private String profile;
    /**
     * 博主昵称
     */
    private String nickName;
    /**
     * 博主签名
     */
    private String sign;
    /**
     * 头像路径
     */
    private String imageName;
}
