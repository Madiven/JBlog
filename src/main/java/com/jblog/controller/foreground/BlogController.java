package com.jblog.controller.foreground;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jblog.lucene.BlogIndex;
import com.jblog.pojo.Blog;
import com.jblog.pojo.CommentVO;
import com.jblog.service.BlogService;
import com.jblog.service.CommentService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/article")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    private BlogIndex blogIndex = new BlogIndex();

    @RequestMapping(value = {"/category/{blogTypeId}/list/{num}", "/category/{blogTypeId}"})
    public ModelAndView getArticles(
            @PathVariable Integer blogTypeId,
            @PathVariable(required = false) Integer num
    ) {
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(num == null ? 1 : num, 10);
        PageInfo<Blog> items = new PageInfo<>(blogService.getArticlesByType(blogTypeId), 5);
        mv.addObject("articles", items);
        mv.setViewName("foreground/bloglist");
        return mv;
    }

    @RequestMapping(value = {"/month/{year}/{month}", "/month/{year}/{month}/list/{num}"})
    public ModelAndView getRecommendArticles(
            @PathVariable String year,
            @PathVariable String month,
            @PathVariable(value = "num", required = false) Integer num
    ) {
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(num == null ? 1 : num, 10);
        String date = year + month;
        PageInfo<Blog> items = new PageInfo<>(blogService.getArticlesByDate(date), 5);
        mv.addObject("articles", items);
        mv.setViewName("foreground/bloglist");
        return mv;
    }

    @RequestMapping(value = {"/recommend/list/{num}"})
    public ModelAndView getRecommendArticles(
            @PathVariable Integer num
    ) {
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(num, 10);
        PageInfo<Blog> items = new PageInfo<>(blogService.getRecommendArticles(), 5);
        mv.addObject("recommendArticles", items);
        mv.setViewName("foreground/index");
        return mv;

    }

    @RequestMapping(value = {"/list", "/list/{pageNum}"})
    public ModelAndView getAllArticles(
            @PathVariable(required = false) Integer pageNum
    ) {
        ModelAndView mv = new ModelAndView();
        PageInfo<Blog> items = blogService.listBlog(pageNum == null ? 1:pageNum, 10, 5);
        mv.addObject("articles", items);
        mv.setViewName("foreground/bloglist");
        return mv;
    }

    @RequestMapping("/detail/{id}")
    public ModelAndView getArticleDetail(
            @PathVariable Integer id
    ) {
        ModelAndView mv = new ModelAndView();
        Blog current = blogService.readBlog(id);
        Blog prev = blogService.getPrevBlog(id);
        Blog next = blogService.getNextBlog(id);
        List<CommentVO> commentVOS = commentService.getByBlogId(id);
        mv.addObject("detail", current);
        mv.addObject("prevBlog", prev);
        mv.addObject("nextBlog", next);
        mv.addObject("comments", commentVOS);
        mv.setViewName("foreground/detail");
        return mv;
    }

    @PostMapping(value = {"/search", "/search/list/{pageNum}"})
    public ModelAndView searchArticles(
            @RequestParam(name="search",required = false) String search,
            @PathVariable(required = false) Integer pageNum
    ) throws ParseException, InvalidTokenOffsetsException, IOException {
        ModelAndView mv = new ModelAndView();
        PageInfo<Blog> info = blogIndex.searchBlog(search, pageNum == null ? 1 : pageNum);
        mv.addObject("articles", info);
        mv.addObject("aboutPage","当前搜索: "+search);
        mv.setViewName("foreground/bloglist");
        return mv;
    }
}
