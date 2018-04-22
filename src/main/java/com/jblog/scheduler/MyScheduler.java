package com.jblog.scheduler;

import com.jblog.dao.BlogDao;
import com.jblog.dao.BlogTypeDao;
import com.jblog.dao.BloggerDao;
import com.jblog.dao.LinkDao;
import com.jblog.pojo.Blog;
import com.jblog.pojo.Blogger;
import com.jblog.pojo.Link;
import com.jblog.pojo.Blog;
import com.jblog.pojo.BlogTypeVO;
import com.jblog.pojo.Blogger;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyScheduler implements Job {

    private static final String BLOGMAPPERNAME = "com.jblog.dao.BlogDao";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        Map dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        ApplicationContext application = (ApplicationContext) dataMap.get("applicationContext");
        BlogDao blogDao = (BlogDao) application.getBean("blogDao");
        BlogTypeDao blogTypeDao = (BlogTypeDao) application.getBean("blogTypeDao");
        BloggerDao bloggerDao = (BloggerDao) application.getBean("bloggerDao");
        LinkDao linkDao = (LinkDao) application.getBean("linkDao");
        SqlSessionFactory factory = (SqlSessionFactory) application.getBean("sqlSessionFactory");
        SqlSession sqlSession = factory.openSession();

        MapResultHandler countMapHandler = new MapResultHandler();
        sqlSession.select(BLOGMAPPERNAME + ".getCount", countMapHandler);
        Map<String, Integer> countMap =  countMapHandler.getMappedResults();
        List<Blog> currentBlogs = blogDao.getCurrent();
        MapResultHandler recordMapHandler = new MapResultHandler();
        sqlSession.select(BLOGMAPPERNAME + ".getRecord", recordMapHandler);
        Map<String, Integer> recordMap =  recordMapHandler.getMappedResults();
        List<Blog> topReadBlogs = blogDao.getTopReadCount();
        List<BlogTypeVO> blogTypes = blogTypeDao.getCount();
        List<Link> links = linkDao.listLinkData();
        Blogger blogger = bloggerDao.getBloggerMsg();
        servletContext.setAttribute("countMap", countMap);
        servletContext.setAttribute("currentBlogs", currentBlogs);
        servletContext.setAttribute("recordMap", recordMap);
        servletContext.setAttribute("topReadBlogs", topReadBlogs);
        servletContext.setAttribute("blogTypes", blogTypes);
        servletContext.setAttribute("links", links);
        servletContext.setAttribute("blogger", blogger);
        System.out.println("正在执行------------------------");
    }

    static class MapResultHandler implements ResultHandler {

        private  final Map mappedResults = new HashMap();
        @Override
        public void handleResult(ResultContext resultContext) {
            Map map = (Map) resultContext.getResultObject();
            mappedResults.put(String.valueOf( map.get("key")), map.get("value"));
        }

        public Map getMappedResults() {
            return mappedResults;
        }

    }
}
