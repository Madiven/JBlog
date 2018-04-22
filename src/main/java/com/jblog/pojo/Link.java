package com.jblog.pojo;

import lombok.Data;

@Data
public class Link {
    private Integer id;
    private String linkName;
    private String linkUrl;
    private Integer orderNum;
}
