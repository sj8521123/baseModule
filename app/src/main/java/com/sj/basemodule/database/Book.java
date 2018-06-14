package com.sj.basemodule.database;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * Created by 13658 on 2018/6/11.
 */

public class Book extends LitePalSupport {
    private int id;
    private String author;
    private double price;
    @Column(ignore = true)
    private int pages;
    private String name;

    public Book( String author, double price, int pages, String name) {
        this.author = author;
        this.price = price;
        this.pages = pages;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author == null ? "" : author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
