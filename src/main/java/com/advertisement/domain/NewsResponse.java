package com.advertisement.domain;

import lombok.Data;

@Data
public class NewsResponse {
    //新闻链接
    private String linked;
    //新闻标题
    private String title;
    //新闻标题图片
    private String titleImage;
}
