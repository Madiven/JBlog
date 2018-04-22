package com.jblog.controller.admin;

import com.jblog.common.bean.EasyUIResult;
import com.jblog.service.BlogTypeService;
import com.jblog.service.BloggerService;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class SysPageController {
    @Autowired
    private BloggerService bloggerService;
    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired()
    private SchedulerFactoryBean scheduler;

    @PostMapping("/refreshSystemCache")
    @ResponseBody
    public EasyUIResult refreshSystemCache() throws Exception {

        scheduler.getScheduler().triggerJob(new JobKey("jobDetail"));

        return new EasyUIResult();
    }

    @RequestMapping("/pagecomponent/{url}.html")
    public String module(@PathVariable("url") String url){
        return "admin/pagecomponent/" + url;
    }

     @RequestMapping("/{url}.html")
    public String url(@PathVariable("url") String url){
        return "admin/" + url;
    }

    @RequestMapping("/")
    public String index(){
        return "admin/login";
    }


}
