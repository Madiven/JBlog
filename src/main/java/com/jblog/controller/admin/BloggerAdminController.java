package com.jblog.controller.admin;

import com.jblog.common.bean.EasyUIResult;
import com.jblog.util.CryptographyUtil;
import com.jblog.util.HttpContextUtil;
import com.jblog.util.PropertiesUtil;
import com.jblog.pojo.Blogger;
import com.jblog.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

    @Autowired
    private BloggerService bloggerService;

    private static final String filePath;

    static {
        try {
            filePath = PropertiesUtil.getProperty("save.properties", "blogger.imagePath");
        } catch (Exception e) {
            throw new RuntimeException("从ueditor.properties配置文件中获取ueditor图片存储位置失败", e);
        }
    }

    //修改博主密码
    @PostMapping("/modifyPassword")
    public EasyUIResult modifyPassword(
            @RequestParam("password") String password) {
        Blogger blogger = new Blogger();
        blogger.setPassword(CryptographyUtil.md5(password));
        return new EasyUIResult(bloggerService.updateBlogger(blogger));
    }

    @PostMapping("/updateUserInfo")
    public EasyUIResult modifyInfo(
            @RequestParam("imageFile") MultipartFile imageFile,
            Blogger blogger) throws IOException {

        if(!imageFile.isEmpty()) { //如果用户有传过照片，就更新
            String imageFileName = imageFile.getOriginalFilename();
            String type = imageFileName.indexOf(".") != -1 ?
                    imageFileName.substring(imageFileName.lastIndexOf(".")+1, imageFileName.length()) : null;
            if (type!=null) {
                if ("GIF".equals(type.toUpperCase())
                        || "PNG".equals(type.toUpperCase())
                        || "JPG".equals(type.toUpperCase())) {
                    HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
                    String imageName = String.valueOf(System.currentTimeMillis()) + "."
                            + type;
                    File file = new File(filePath);
                    if(!file.exists()) {
                        file.mkdirs();
                    }
                    imageFile.transferTo(new File(filePath + File.separator + imageName));
                    System.out.println(request.getContextPath());
                    String[] temp = filePath.split("/");
                    blogger.setImageName(request.getContextPath()+ "/" + temp[temp.length - 1] + "/" + imageName);
                } else {
                    return new EasyUIResult("上传文件不是图片类型，请重新上传！",1);
                }
            } else {
                return new EasyUIResult("上传文件类型为空，请重新上传！",1);
            }
        }
        return new EasyUIResult(bloggerService.updateBlogger(blogger));
    }

    @PostMapping("/findBlogger")
    public EasyUIResult modifyInfo() throws IOException {
        return new EasyUIResult(bloggerService.getBlogger());
    }
}
