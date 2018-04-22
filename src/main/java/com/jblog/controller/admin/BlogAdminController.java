package com.jblog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.jblog.common.bean.EasyUIResult;
import com.jblog.lucene.BlogIndex;
import com.jblog.pojo.Blog;
import com.jblog.pojo.CommentVO;
import com.jblog.service.BlogService;
import com.jblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/admin/blog")
public class BlogAdminController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    private BlogIndex blogIndex = new BlogIndex();

    @RequestMapping("/listBlog")
    public EasyUIResult listBlogType(
            @RequestParam(value = "page",defaultValue="1") Integer page,
            @RequestParam(value = "rows", defaultValue="10") Integer rows
    ) {
        PageInfo<Blog> pageInfo = blogService.listBlog(page, rows);
        return new EasyUIResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @RequestMapping("/searchBlog")
    public EasyUIResult searchBlog(
            @RequestParam(value = "page",defaultValue="1") Integer page,
            @RequestParam(value = "rows", defaultValue="10") Integer rows,
            @RequestParam(value = "title") String title
    ) {
        PageInfo<Blog> pageInfo = blogService.searchBlogByTitle(title,page, rows);
        return new EasyUIResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping("/save")
    public EasyUIResult<Integer> save(Blog blog) throws Exception {
        EasyUIResult<Integer> result = new EasyUIResult<>(blogService.save(blog));
        blogIndex.addIndex(blog);
        return result;
    }

    @PostMapping("/update")
    public EasyUIResult<Integer> update(Blog blog) throws Exception {
        blogIndex.updateIndex(blog);
        return new EasyUIResult<>(blogService.update(blog)) ;
    }

    @PostMapping("/delete")
    public EasyUIResult<Integer> delete(String ids) throws Exception {
        String[] temp = ids.split(",");
        Integer[] idList = new Integer[temp.length];

        for (int i = 0; i < temp.length; i++) {
            idList[i] = Integer.parseInt(temp[i]);
        }
        blogService.deleteBlog(idList);
        blogIndex.deleteIndex(temp);
        return new EasyUIResult<>() ;
    }

    @PostMapping("/getBlogById")
    public EasyUIResult<Blog> getBlogById(Integer id) {
        return new EasyUIResult<>(blogService.getBlogById(id));
    }

    @RequestMapping("/detail/{id}")
    public ModelAndView getArticleDetail(
            @PathVariable Integer id
    ) {
        ModelAndView mv = new ModelAndView();
        Blog current = blogService.getBlogById(id);
        List<CommentVO> commentVOS = commentService.getByBlogId(id);
        mv.addObject("detail", current);
        mv.addObject("comments", commentVOS);
        mv.setViewName("admin/pagecomponent/blogDetail");
        return mv;
    }
}
