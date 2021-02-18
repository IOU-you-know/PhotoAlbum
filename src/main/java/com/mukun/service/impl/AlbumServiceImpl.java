package com.mukun.service.impl;

import com.mukun.dao.AlbumDao;
import com.mukun.domain.Album;
import com.mukun.service.AlbumService;

import java.util.List;
public class AlbumServiceImpl implements AlbumService {
    private AlbumDao albumDao;

    public void setAlbumDao(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    @Override
    public boolean save(Album album) {
        return albumDao.save(album);
    }

    @Override
    public boolean delete(Album album) {
        return albumDao.delete(album);
    }

    @Override
    public boolean update(Album album) {
        return albumDao.update(album);
    }

    @Override
    public Album findById(Album album) {
        return albumDao.findById(album);
    }

    @Override
    public List<AlbumDao> findAll() {
        return albumDao.findAll();
    }
}
