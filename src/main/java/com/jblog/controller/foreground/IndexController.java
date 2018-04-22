package com.jblog.controller.foreground;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jblog.pojo.Blog;
import com.jblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage( 1,10);
        PageInfo<Blog> items = new PageInfo<>(blogService.getRecommendArticles(), 5);
        mv.addObject("recommendArticles", items);
        mv.setViewName("foreground/index");
        return mv;
    }
    @RequestMapping("/about_me")
    public ModelAndView aboutMe() {
        return new ModelAndView("foreground/about");
    }
}
