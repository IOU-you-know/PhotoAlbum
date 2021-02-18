package com.mukun.domain;

import java.util.Date;
import java.util.StringJoiner;

public class Picture {
    private Integer id;
    private String name;
    private Integer album_id;
    private Date p_date;
    private String note;

    @Override
    public String toString() {
        return new StringJoiner(", ", Picture.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("album_id=" + album_id)
                .add("p_date=" + p_date)
                .add("note='" + note + "'")
                .toString();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getP_date() {
        return p_date;
    }

    public void setP_date(Date p_date) {
        this.p_date = p_date;
    }

    public Picture(String name, Integer album_id, Date p_date) {
        this.name = name;
        this.album_id = album_id;
        this.p_date = p_date;
    }

    public Picture(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Picture(String name) {
        this.name = name;
    }

    public Picture(Integer album_id) {
        this.album_id = album_id;
    }

    public Picture(String name, Integer album_id) {
        this.name = name;
        this.album_id = album_id;
    }

    public Picture(Integer id, String name, Integer album_id) {
        this.id = id;
        this.name = name;
        this.album_id = album_id;
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

    public Integer getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(Integer album_id) {
        this.album_id = album_id;
    }

    public Picture() {
    }

}
