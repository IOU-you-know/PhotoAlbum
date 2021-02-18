package com.mukun.service;

import com.mukun.domain.Picture;

import java.util.List;

public interface PictureService {
    boolean save(Picture picture);
    boolean delete(Picture picture);
    boolean update(Picture picture);
    Picture findById(Picture picture);
    List<Picture> findAll(Picture picture);
}
