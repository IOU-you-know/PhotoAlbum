package com.mukun.web;

import com.mukun.dao.AlbumDao;
import com.mukun.domain.Album;
import com.mukun.domain.Picture;
import com.mukun.service.AlbumService;
import com.mukun.service.PictureService;
import com.mukun.web.results.Code;
import com.mukun.web.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Album")
public class AlbumSpringMVC {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private PictureService pictureService;
    @RequestMapping("/findAll")
    @ResponseBody
    @CrossOrigin
    public Result findAll(){
        List<AlbumDao> all = albumService.findAll();
        return new Result(null != all? Code.GET_OK:Code.GET_ERROR,all);
    }
    @RequestMapping("/delete")
    @ResponseBody
    @CrossOrigin
    public Result delete(Integer id, HttpServletRequest request) {
        Picture picture = new Picture();
        picture.setAlbum_id(id);
        List<Picture> all = pictureService.findAll(picture);
        for (Picture picture1 : all) {
            System.out.println(picture1.getName());
            new File(request.getServletContext().getRealPath("img")+"\\"+picture1.getName()).delete();
        }
        Album album = new Album();
        album.setId(id);
        boolean flag = albumService.delete(album);
        return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERROR);
    }
    @RequestMapping("/add")
    @ResponseBody
    @CrossOrigin
    public Result add(String name, HttpServletResponse response){
        Date date = new Date();
        Album album = new Album();
        album.setName(name);
        album.setA_date(date);
        boolean flag = albumService.save(album);
        return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERROR);
    }
}
