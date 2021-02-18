package com.mukun.dao;

import com.mukun.domain.Picture;

import java.util.List;

public interface PictureDao {
//    @Insert("insert into picture(name,album_id,p_date) values(#{name},#{album_id},#{p_date})")
    boolean save(Picture picture);
//    @Delete("delete from picture where id = #{id}")
    boolean delete(Picture picture);
//    @Update("update picture set note = #{note} where id = #{id}")
    boolean update(Picture picture);
//    @Select(" select * from picture where id = #{id}")
    Picture findById(Picture picture);
//    @Select("select * from picture where album_id = #{album_id} order by p_date desc")
    List<Picture> findAll(Picture picture);
}