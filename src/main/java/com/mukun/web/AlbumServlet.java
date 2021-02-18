//package com.mukun.web;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mukun.dao.AlbumDao;
//import com.mukun.domain.Album;
//import com.mukun.domain.Picture;
//import com.mukun.service.AlbumService;
//import com.mukun.service.PictureService;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.net.URLDecoder;
//import java.util.Date;
//import java.util.List;
//
//@WebServlet("/AlbumServlet")
//public class AlbumServlet extends HttpServlet {
//    ApplicationContext ctx = new ClassPathXmlApplicationContext("Application.xml");
//    AlbumService albumService = (AlbumService)ctx.getBean("AlbumService");
//    PictureService pictureService = (PictureService)ctx.getBean("PictureService");
//    private ObjectMapper mapper = new ObjectMapper();
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if ("add".equals(req.getParameter("option"))){
//            addPAlbum(req,resp);
//        } else if ("delete".equals(req.getParameter("option"))){
//            deleteAlbum(req,resp);
//        } else if ("findAll".equals(req.getParameter("option"))){
//            findAll(req,resp);
//        }
//    }
//
//    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        List<AlbumDao> all = albumService.findAll();
//        String json = mapper.writeValueAsString(all);
//        resp.getWriter().write(json);
//    }
//
//    private void addPAlbum(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String name = URLDecoder.decode(req.getParameter("name"), "UTF-8");
//        Date date = new Date();
//        //String name = req.getParameter("name");
//        Album album = new Album(name);
//        album.setA_date(date);
//        albumService.save(album);
//        resp.sendRedirect("Album.html");
//    }
//
//    private void deleteAlbum(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String id = req.getParameter("id");
//        Picture picture = new Picture();
//        picture.setAlbum_id(Integer.valueOf(id));
//        List<Picture> all = pictureService.findAll(picture);
//        for (Picture picture1 : all) {
//            System.out.println(picture1.getName());
//            new File(this.getServletContext().getRealPath("img")+"\\"+picture1.getName()).delete();
//        }
//        Album album = new Album(Integer.valueOf(id));
//        albumService.delete(album);
//        //resp.sendRedirect("Album.html");
//    }
//
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doPost(req, resp);
//    }
//}