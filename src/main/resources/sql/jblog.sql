DROP DATABASE IF EXISTS blog_db;
/*创建数据库，并设置编码*/
CREATE DATABASE blog_db DEFAULT CHARACTER SET utf8;

USE blog_db;

CREATE TABLE `t_blogger` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '博主id',
  `username` VARCHAR(50) NOT NULL COMMENT '博主姓名',
  `password` VARCHAR(100) NOT NULL COMMENT '博主密码',
  `profile` TEXT COMMENT '博主信息',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '博主昵称',
  `sign` VARCHAR(100) DEFAULT NULL COMMENT '博主签名',
  `imagename` VARCHAR(100) DEFAULT NULL COMMENT '博主头像路径',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `t_link` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '友情链接表id',
  `linkname` VARCHAR(100) DEFAULT NULL COMMENT '友情链接名',
  `linkurl` VARCHAR(200) DEFAULT NULL COMMENT '友情链接url',
  `orderNum` INT(11) DEFAULT NULL COMMENT '链接排序',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE `t_blogtype` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `typeName` VARCHAR(30) DEFAULT NULL COMMENT '博客类别',
  `orderNum` INT(11) DEFAULT NULL COMMENT '博客排序',
  `isShow` TINYINT(1) DEFAULT 1 COMMENT '是否显示，1：显示；0：不显示，默认为1',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

CREATE TABLE `t_blog` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '博客类型',
  `title` VARCHAR(200) NOT NULL COMMENT '博客题目',
  `summary` VARCHAR(400) DEFAULT NULL COMMENT '博客摘要',
  `releaseDate` DATETIME DEFAULT NULL COMMENT '发布日期',
  `readCount` INT(11) DEFAULT NULL COMMENT '评论次数',
  `commentCount` INT(11) DEFAULT NULL COMMENT '回复次数',
  `content` LONGTEXT COMMENT '博客内容',
  `keyWord` VARCHAR(200) DEFAULT NULL COMMENT '关键字',
  `blogType` INT(1) DEFAULT NULL COMMENT '博客类型，1：原创；2：转载；3：翻译',
  `isShow` TINYINT(1) DEFAULT 1 COMMENT '是否显示，1：显示；0：不显示，默认为1',
  `isReply` TINYINT(1) DEFAULT 1 COMMENT '是否可以评论，1：可以；0：不可以，默认为1',
  `isCommend` TINYINT(1) DEFAULT 0 COMMENT '是否推荐，1：推荐；0：不推荐，默认为0',
  `type_id` INT(11) DEFAULT NULL COMMENT '外键关联博客类别',
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `fk_blog_blogType` FOREIGN KEY (`type_id`) REFERENCES `t_blogtype` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

CREATE TABLE `t_comment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '评论表id',
  `userIp` VARCHAR(50) DEFAULT NULL COMMENT '评论者的ip',
  `userName` VARCHAR(50) DEFAULT NULL COMMENT '评论名称',
  `content` VARCHAR(1000) DEFAULT NULL COMMENT '评论内容',
  `commentDate` DATETIME DEFAULT NULL COMMENT '评论日期',
  `state` INT(1) DEFAULT 0 COMMENT '是否通过审核，0：待审核， 1：审核通过， 2：未通过，默认为0',
  `to_id` VARCHAR(11) DEFAULT NULL COMMENT '父评论ID',
  `blog_id` INT(11) DEFAULT NULL COMMENT '外键关联具体博客',
  PRIMARY KEY (`id`),
  KEY `blog_id` (`blog_id`),
  CONSTRAINT `fk_comment_blog` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

insert into t_blogger values (null,'admin','bd0e1efa6976956bb6d9fe5dd243adec','','小麦','精感石没羽，岂云惮险艰？','');
