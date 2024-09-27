package com.example.appxemphim_nhom6.data.model;

import java.util.List;

public class Movie {
    private String name;
    private String slug;
    private String content;
    private int year;
    private List<String> actor;
    private List<String> director;
    private String poster_url; // Thuộc tính để lưu URL của poster

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getActor() {
        return actor;
    }

    public void setActor(List<String> actor) {
        this.actor = actor;
    }

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }

    public String getPosterUrl() { // Phương thức getter cho 'poster_url'
        return poster_url;
    }

    public void setPosterUrl(String poster_url) {
        this.poster_url = poster_url;
    }
}




