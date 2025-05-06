package com.example.appdev_buksu_it_capstone_archives;


import java.util.List;
public class CapstoneModel {
    private String title;
    private String authors;
    private String category;

    public CapstoneModel(String title, String authors, String category) {
        this.title = title;
        this.authors = authors;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getCategory() {
        return category;
    }
}
