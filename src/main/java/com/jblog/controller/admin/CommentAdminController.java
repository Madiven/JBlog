package com.jblog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.jblog.common.bean.EasyUIResult;
import com.jblog.common.ip.IpHelper;
import com.jblog.util.HttpContextUtil;
import com.jblog.util.IPUtil;
import com.jblog.util.RegexUtil;
import com.jblog.pojo.Comment;
import com.jblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/admin/comment")
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    private final static Pattern P_COMMENTS = Pattern.compile("^\\[reply].*?\\[.*reply](\r\n)*");

    private final static Pattern P_NEWLINE = Pattern.compile("\r\n");

    @RequestMapping("/listComment")
    public EasyUIResult<Comment> listComment(
            @RequestParam(value = "page",defaultValue="1") Integer page,
            @RequestParam(value = "rows", defaultValue="10") Integer rows
    ) {
        PageInfo<Comment> pageInfo = commentService.listComment(page, rows);
        return new EasyUIResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
    @RequestMapping("/commentReview")
    public EasyUIResult<Comment> listComment2Review(
            @RequestParam(value = "page",defaultValue="1") Integer page,
            @RequestParam(value = "rows", defaultValue="10") Integer rows
    ) {
        PageInfo<Comment> pageInfo = commentService.listComment2Review(page, rows);
        return new EasyUIResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping("/review")
    public EasyUIResult<Integer> updateState(
            @RequestParam(value = "state") Integer state,
            @RequestParam(value = "ids") String ids
    ) {
        String[] temp = ids.split(",");
        Integer[] idList = new Integer[temp.length];
        for (int i = 0; i < temp.length; i++) {
            idList[i] = Integer.parseInt(temp[i]);
        }
        return new EasyUIResult<>(commentService.update(state, idList));
    }

    @PostMapping("/deleteComment")
    public EasyUIResult<Integer> deleteComment(
            @RequestParam(value = "ids") String ids
    ) {
        String[] temp = ids.split(",");
        Integer[] idList = new Integer[temp.length];
        for (int i = 0; i < temp.length; i++) {
            idList[i] = Integer.parseInt(temp[i]);
        }
        return new EasyUIResult<>(commentService.deleteComment(idList));
    }

    @PostMapping("/submit")
    public EasyUIResult<Integer> submit(Comment comment) {
        String content = comment.getContent();
        content = RegexUtil.regexReplace(P_COMMENTS, "", content);
        comment.setUserName("博主");
        content = RegexUtil.regexReplace(P_NEWLINE,"<br/>", content);
        comment.setContent(content);
        comment.setState(1);
        return new EasyUIResult<>(commentService.addComment(comment));
    }
}
