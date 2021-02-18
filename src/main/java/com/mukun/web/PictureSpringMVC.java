package com.mukun.web;


import com.mukun.domain.Album;
import com.mukun.domain.Picture;
import com.mukun.service.AlbumService;
import com.mukun.service.PictureService;
import com.mukun.web.results.Code;
import com.mukun.web.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/Picture")
public class PictureSpringMVC {
    @Autowired
    private PictureService pictureService;
    @Autowired
    private AlbumService albumService;
    private static Integer img_id = null;
    @RequestMapping("/findAll")
    @ResponseBody
    @CrossOrigin
    public Result findAll(int id){
        img_id = id;
        Picture picture = new Picture();
        picture.setAlbum_id(id);
        List<Picture> all = pictureService.findAll(picture);
        return new Result(null != all ? Code.GET_OK:Code.GET_ERROR,all);
    }
    @RequestMapping("/add")
    @ResponseBody
    @CrossOrigin
    public Result add(MultipartFile file, Integer id, HttpServletRequest request) throws IOException {
        boolean flag = false;
        if ("image".equals(file.getContentType().substring(0,5))){
            String uuid = UUID.randomUUID().toString();
            file.transferTo(new File(request.getServletContext().getRealPath("img"), uuid+file.getOriginalFilename()));
            Picture picture = new Picture();
            picture.setAlbum_id(id);
            picture.setName(uuid+file.getOriginalFilename());
            picture.setP_date(new Date());
            pictureService.save(picture);
            Album album = new Album();
            album.setId(id);
            album.setDefault_img(uuid+file.getOriginalFilename());
            flag = albumService.update(album);
        }
        return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERROR);
    }
    @RequestMapping("/delete")
    @ResponseBody
    @CrossOrigin
    public Result delete(Integer id,String name,HttpServletRequest request){
        System.out.println(id);
        System.out.println(name);
        Album album = new Album();
        System.out.println(img_id);
        album.setId(img_id);
        Picture picture = new Picture();
        picture.setId(id);
        picture.setName(name);
        boolean flag = pictureService.delete(picture);
        new File(request.getServletContext().getRealPath("img")+"\\"+name).delete();
        if (name.equals(albumService.findById(album).getDefault_img())){
            Picture picture1 = new Picture();
            picture1.setAlbum_id(img_id);
            List<Picture> all = pictureService.findAll(picture);
            if (all.size() == 0){
                album.setDefault_img("empty.jpg");
                albumService.update(album);
            } else {
                String name1 = all.get(0).getName();
                album.setDefault_img(name1);
                albumService.update(album);
            }
        }
        return new Result(flag ? Code.DELETE_OK:Code.DELETE_OK);
    }
    @RequestMapping("/edit")
    @ResponseBody
    @CrossOrigin
    private Result edit(Integer id, @RequestBody String note, HttpServletRequest request) throws IOException {
        Picture picture = new Picture();
        picture.setId(id);
        picture.setNote(note);
        System.out.println(note);
        boolean flag = pictureService.update(picture);
        return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERROR);
    }
    @RequestMapping("/findNote")
    @ResponseBody
    @CrossOrigin
    private Result findNote(Integer id){
        Picture picture = new Picture();
        picture.setId(id);
        Picture picture1 = pictureService.findById(picture);
        System.out.println(picture1);
        if (picture1.getNote() == null){
            String data = "[]";
            return new Result(Code.GET_ERROR,data);
        } else {
            String data = picture1.getNote().substring(9,picture1.getNote().length()-2).replaceAll("\\\\","");
            System.out.println(1111);
            System.out.println(data);
            System.out.println(2222);
            return new Result(Code.GET_OK,data);
        }
    }
}
