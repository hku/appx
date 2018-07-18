package com.app.assistant.entity;

import com.app.assistant.base.BaseEntity;

import java.util.List;

/**
 * author: zhanghe
 * created on: 2018/7/17 17:08
 * description:
 */

public class PoetEntity extends BaseEntity {

    private List<String> strains;
    private String author;
    private List<String> paragraphs;
    private String title;

    public List<String> getStrains() {
        return strains;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public String getTitle() {
        return title;
    }

    public void setStrains(List<String> strains) {
        this.strains = strains;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
