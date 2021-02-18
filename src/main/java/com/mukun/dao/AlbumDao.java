package com.mukun.dao;

import com.mukun.domain.Album;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AlbumDao {
//    @Insert("insert into album(name,a_date) values(#{name},#{a_date})")
    boolean save(Album album);
//    @Delete("delete from album where id = #{id}")
    boolean delete(Album album);
//    @Update("update Album set default_img = #{default_img} WHERE id = #{id}")
    boolean update(Album album);
//    @Select("select * from album where id = #{id}")
    Album findById(Album album);
//    @Select("select * from album order by a_date desc")
    List<AlbumDao> findAll();
}