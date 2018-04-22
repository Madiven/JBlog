package com.jblog.lucene;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jblog.common.lucene.IKAnalyzer5x;
import com.jblog.dao.BlogDao;
import com.jblog.pojo.Blog;
import com.jblog.util.PropertiesUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class BlogIndex {

    private static BlogDao blogDao;
    private static String savePath;

    static {
        try{
            savePath = PropertiesUtil.getProperty("save.properties", "lucence.savePath");
            ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext*.xml");
            blogDao = (BlogDao) context.getBean("blogDao");
        } catch (Exception e) {
            throw new RuntimeException("从savePath.properties配置文件中获取lucence索引存储位置失败", e);
        }

    }

    private IndexWriter getWriter() throws Exception {
        Directory dir = FSDirectory.open(Paths.get("F:\\blog_index"));
        Analyzer analyzer = new IKAnalyzer5x();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }

    //添加博客索引
    public void addIndex(Blog blog)  throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }

    //删除指定博客的索引
    public void deleteIndex(String blogId) throws Exception {
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term("id", blogId));
        writer.forceMergeDeletes();//强制删除
        writer.commit();
        writer.close();
    }

    public void deleteIndex(String[] ids)throws Exception {
        IndexWriter writer = getWriter();
        for (String id : ids) {
            writer.deleteDocuments(new Term("id", id));
            writer.forceMergeDeletes();//强制删除
            writer.commit();
        }
        writer.close();
    }

    //更新博客索引
    public void updateIndex(Blog blog) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
        writer.updateDocument(new Term("id", String.valueOf(blog.getId())), doc);
        writer.close();
    }

    public PageInfo<Blog> searchBlog(String q, int pageNum) throws IOException, ParseException, InvalidTokenOffsetsException {
        Directory dir = FSDirectory.open(Paths.get("F:\\blog_index"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        Analyzer analyzer = new IKAnalyzer5x();

        QueryParser parser1 = new QueryParser("title", analyzer);
        Query query1 = parser1.parse(q);
        QueryParser parser2 = new QueryParser("content", analyzer);
        Query query2 = parser2.parse(q);
        builder.add(query1, BooleanClause.Occur.SHOULD);
        builder.add(query2, BooleanClause.Occur.SHOULD);

        int count = reader.maxDoc();
        TopDocs hits = searcher.search(builder.build(), count == 0 ? 1 : count);
        QueryScorer scorer = new QueryScorer(query1);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);

        List<Blog> blogList = new LinkedList<Blog>();
        ScoreDoc[] docs = hits.scoreDocs;
        int startPage = (pageNum - 1) * 10;
        int endPage = pageNum * 10 > docs.length ? docs.length : pageNum * 10;
        for(int i = startPage; i < endPage; i++) {
            Document doc = searcher.doc(docs[i].doc);
            Blog blog = blogDao.findById(Integer.parseInt(doc.get("id")));
            String title = doc.get("title");
            String content = doc.get("content");
            if(title != null) {
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                String hTitle = highlighter.getBestFragment(tokenStream, title);
                if(StringUtil.isEmpty(hTitle)) {
                    blog.setTitle(title);
                } else {
                    blog.setTitle(hTitle);
                }
            }
            if(content != null) {
                TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
                String hContent = highlighter.getBestFragment(tokenStream, content);
                if(StringUtil.isEmpty(hContent)) {
                    if(content.length() > 200) {
                        blog.setSummary(content.substring(0, 200));
                    } else {
                        blog.setSummary(content);
                    }
                } else {
                    blog.setSummary(hContent);
                }
            }
            blogList.add(blog);
        }
        reader.close();
        dir.close();
        return getPageInfo(blogList, docs, pageNum);
    }

    private PageInfo<Blog> getPageInfo(List<Blog> blogList, ScoreDoc[] docs, int pageNum) {
        PageInfo<Blog> info = new PageInfo<>(blogList, 5);
        int endPage = docs.length / 10 + 1;
        info.setPages(endPage);
        info.setTotal(docs.length);
        info.setIsFirstPage(pageNum == 1);
        info.setIsLastPage(pageNum == endPage);
        info.setPageNum(pageNum);
        int navigateFirstPage = (pageNum - 1) / 5 * 5 + 1;
        int tempNavigateLastPage = ((pageNum - 1) / 5 + 1) * 5;
        int navigateLastPage = tempNavigateLastPage < endPage ? tempNavigateLastPage : endPage;
        info.setNavigateFirstPage(navigateFirstPage);
        info.setNavigateLastPage(navigateLastPage);
        int[] navigatepageNums = new int[navigateLastPage - navigateFirstPage |+ 1];
        for(int i = 0; i < navigatepageNums.length; i++) {
            navigatepageNums[i] = navigateFirstPage + i;
        }
        info.setNavigatepageNums(navigatepageNums);
        return info;
    }
}
