package com.jblog.controller.admin;

import com.jblog.common.bean.EasyUIResult;
import com.jblog.util.CryptographyUtil;
import com.jblog.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private BloggerService bloggerService;

    @PostMapping("/admin/login")
    public EasyUIResult main(
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "password") String password
    ) {

        Subject subject = SecurityUtils.getSubject();//获取当前登陆的主体
        String newPassword = CryptographyUtil.md5(password);//将密码使用md5加密
        UsernamePasswordToken token = new UsernamePasswordToken(userName, newPassword);
        try {
            subject.login(token); //会调用MyRealm中的doGetAuthenticationInfo方法进行身份认证
        } catch (Exception e) {
            return new EasyUIResult("用户名或密码错误", EasyUIResult.FAIL);
        }
        return new EasyUIResult();
    }

    // 退出
    @RequestMapping("/admin/logout")
    public void logout() throws Exception {
        SecurityUtils.getSubject().logout();
    }
}
