package com.mukun.service;

import com.mukun.dao.AlbumDao;
import com.mukun.domain.Album;

import java.util.List;

public interface AlbumService {
    boolean save(Album album);
    boolean delete(Album album);
    boolean update(Album album);
    Album findById(Album album);
    List<AlbumDao> findAll();
}
