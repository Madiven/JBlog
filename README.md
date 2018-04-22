## JBlog
## 练手项目，基于Springmvc+MyBatis+Spring+Html+Freemaker+EasyUI+Mysql的个人博客系统
## 介绍：
>1. 使用Maven3+Spring4+Springmvc+Mybatis3架构；数据库使用Mysql，数据库连接池使用阿里巴巴的Druid；
    页面使用FreeMaker模板引擎。
>2. 博客前台的头部及侧边栏的数据使用quartz按时间间隔去跑数据，并存储于ServletContext，
    可以通过后台管理系统手动刷新数据，评论博客需要填写验证码，使用了kaptcha，并使用lucene实现了站内搜索功能。
>3. 使用EasyUI实现后台对博客、博客类别、用户评论、博主信息的管理；使用shiro实现登陆验证跟登陆后才能使用后台进行管理
    使用UEditor编辑博客,修改了UEditor的源码，支持配置文件上传路径。
## 博客页面
### 博客主页显示
![博客主页显示](https://github.com/Madiven/JBlog/blob/master/readmeImages/01.png)
### 侧边栏显示
![侧边栏显示](https://github.com/Madiven/JBlog/blob/master/readmeImages/02.png)
### 所有文章页面显示
![所有文章页面](https://github.com/Madiven/JBlog/blob/master/readmeImages/03.png)
### 关于我页面显示
![关于我页面显示](https://github.com/Madiven/JBlog/blob/master/readmeImages/04.png)
### 博客内容页面显示
![博客内容页面显示](https://github.com/Madiven/JBlog/blob/master/readmeImages/05.png)
### 发表评论显示
![发表评论显示](https://github.com/Madiven/JBlog/blob/master/readmeImages/13.png)
### 验证码窗口显示
![验证码窗口显示](https://github.com/Madiven/JBlog/blob/master/readmeImages/06.png)
### 博客评论显示
![博客评论显示](https://github.com/Madiven/JBlog/blob/master/readmeImages/08.png)
### 搜索博客
![搜索博客](https://github.com/Madiven/JBlog/blob/master/readmeImages/14.png)

## 博客管理后台页面
### 登陆页面显示
![登陆页面显示](https://github.com/Madiven/JBlog/blob/master/readmeImages/09.png)
### 后台主页显示
![后台主页显示](https://github.com/Madiven/JBlog/blob/master/readmeImages/10.png)
### 编辑博客
![编辑博客](https://github.com/Madiven/JBlog/blob/master/readmeImages/11.png)
### 博客信息管理页面显示
![博客信息管理页面显示](https://github.com/Madiven/JBlog/blob/master/readmeImages/12.png)
其它管理页面跟博客信息管理页面大同小异。

