package com.jblog.controller.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.jblog.common.bean.EasyUIResult;
import com.jblog.pojo.BlogType;
import com.jblog.service.BlogService;
import com.jblog.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {
    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BlogService blogService;

    // 分页查询博客类别
    @RequestMapping("/listBlogType")
    public EasyUIResult listBlogType(
            @RequestParam(value = "page",defaultValue="1") Integer page,
            @RequestParam(value = "rows", defaultValue="10") Integer rows
    ) {
        PageInfo<BlogType> pageInfo = blogTypeService.listBlogType(page, rows);
        return new EasyUIResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping("/newBlogType")
    public EasyUIResult<Integer> save(BlogType blogType) {
        return new EasyUIResult<>(blogTypeService.save(blogType));
    }

    @PostMapping("/updateBlogType")
    public EasyUIResult<Integer> upadate(BlogType blogType) {
        return new EasyUIResult<>(blogTypeService.update(blogType));
    }

    @PostMapping("/deleteBlogType")
    public EasyUIResult<String> delete(@RequestParam(value = "blogTypes", required = false) String blogTypes) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<BlogType> list = mapper.readValue(blogTypes, new TypeReference<List<BlogType>>(){});
        return new EasyUIResult<>(blogTypeService.deleteBlogType(list));
    }

}
