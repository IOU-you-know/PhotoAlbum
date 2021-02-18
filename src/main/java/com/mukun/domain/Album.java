package com.mukun.domain;

import java.util.Date;

public class Album {
    private Integer id;
    private String name;
    private String default_img;
    private Date a_date;

    public Album(Integer id) {
        this.id = id;
    }

    public Date getA_date() {
        return a_date;
    }

    public void setA_date(Date a_date) {
        this.a_date = a_date;
    }

    public String getDefault_img() {
        return default_img;
    }

    public void setDefault_img(String default_img) {
        this.default_img = default_img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Album(String name) {
        this.name = name;
    }

    public Album() {
    }
}
