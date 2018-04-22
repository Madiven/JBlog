package com.jblog.controller.foreground;

import com.jblog.common.bean.Result;
import com.jblog.common.ip.IpHelper;
import com.jblog.util.HttpContextUtil;
import com.jblog.util.IPUtil;
import com.jblog.util.RegexUtil;
import com.jblog.pojo.Comment;
import com.jblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final static Pattern P_COMMENTS = Pattern.compile("^\\[reply].*?\\[.*reply](\r\n)*");

    private final static Pattern P_NEWLINE = Pattern.compile("\r\n");

    @Autowired
    private CommentService commentService;

    @PostMapping("/submit")
    @ResponseBody
    public Result save(Comment comment) {
        try {
            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
            String content = comment.getContent();
            if(comment.getParentId() != null) {
                content = RegexUtil.regexReplace(P_COMMENTS, "", content);
            }
            String ip = IPUtil.getIpAddr(request);
            String userName = IpHelper.findRegionByIp(ip);
            comment.setUserName(userName);
            content = RegexUtil.regexReplace(P_NEWLINE,"<br/>", content);
            comment.setContent(content);
            comment.setState(0);
            commentService.addComment(comment);
            return new Result();
        } catch (Exception e) {
            return new Result(Result.FAIL);
        }
    }
}
