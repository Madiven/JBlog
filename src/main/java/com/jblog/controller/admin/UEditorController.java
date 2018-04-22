package com.jblog.controller.admin;

import com.baidu.ueditor.ActionEnter;
import com.jblog.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/static/ueditor")
public class UEditorController {

    private static String saveRootPath;


    static {
        try {
            saveRootPath = PropertiesUtil.getProperty("save.properties", "ueditor.imagePath");
        } catch (Exception e) {
            throw new RuntimeException("从ueditor.properties配置文件中获取ueditor图片存储位置失败", e);
        }
    }

    @RequestMapping("/jsp/controller")
    public void writePage(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        request.setCharacterEncoding( "utf-8" );
        response.setHeader("Content-Type" , "text/html");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        System.out.println(rootPath);
        System.out.println(saveRootPath);
        String action = request.getParameter("action");
        String result = new ActionEnter(request, rootPath, saveRootPath).exec();
//        if( "listfile".equals(action) ||"listimage".equals(action) ){
//            result = result.replaceAll(saveRootPath, "/");
//        }
        response.getWriter().write(result);
    }
}
