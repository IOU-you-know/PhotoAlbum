package com.mukun.service.impl;

import com.mukun.dao.PictureDao;
import com.mukun.domain.Picture;
import com.mukun.service.PictureService;

import java.util.List;

public class PictureServiceImpl implements PictureService {
    private PictureDao pictureDao;

    public void setPictureDao(PictureDao pictureDao) {
        this.pictureDao = pictureDao;
    }

    @Override
    public boolean save(Picture picture) {
        return pictureDao.save(picture);
    }

    @Override
    public boolean delete(Picture picture) {
        return pictureDao.delete(picture);
    }

    @Override
    public boolean update(Picture picture) {
        return pictureDao.update(picture);
    }

    @Override
    public Picture findById(Picture picture) {
        return pictureDao.findById(picture);
    }

    @Override
    public List<Picture> findAll(Picture picture) {
        return pictureDao.findAll(picture);
    }

}
