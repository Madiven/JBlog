package com.jblog.realm;

import com.jblog.pojo.Blogger;
import com.jblog.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm{

    @Autowired
    private BloggerService bloggerService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String)token.getPrincipal();
        Blogger blogger = bloggerService.getByUserName(username);
        if (blogger != null) {
            SecurityUtils.getSubject().getSession().setAttribute("currentBlogger", blogger);
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
                    blogger.getUserName(), blogger.getPassword(), "MyRealm");
            return authcInfo;
        } else {
            return null;
        }
    }
}
