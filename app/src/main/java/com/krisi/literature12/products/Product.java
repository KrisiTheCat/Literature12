package com.krisi.literature12.products;

import android.util.Pair;

import java.util.ArrayList;

public class Product {
    private String title;
    private String authorName;
    private ProductGenre genre;
    private ProductStyle style;
    private ProductTheme theme;
    private String text;
    private String resume = null;
    private ArrayList<Pair<Integer, String>> chapters;

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public ProductGenre getGenre() {
        return genre;
    }

    public ProductStyle getStyle() {
        return style;
    }

    public String getText() {
        return text;
    }

    public ProductTheme getTheme() {
        return theme;
    }

    public void setTheme(ProductTheme theme) {
        this.theme = theme;
    }

    public String getResume() {
        return resume;
    }

    public ArrayList<Pair<Integer, String>> getChapters() {
        return chapters;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", genre=" + genre +
                ", style=" + style +
                ", theme=" + theme +
                ", text='" + text + '\'' +
                '}';
    }
}
