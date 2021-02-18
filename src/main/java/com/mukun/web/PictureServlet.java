//package com.mukun.web;
//
//import com.alibaba.fastjson.JSON;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mukun.domain.Album;
//import com.mukun.domain.Picture;
//import com.mukun.service.AlbumService;
//import com.mukun.service.PictureService;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.commons.io.IOUtils;
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
//import java.util.*;
//
//@WebServlet("/PictureServlet")
//public class PictureServlet extends HttpServlet {
//    ApplicationContext ctx = new ClassPathXmlApplicationContext("Application.xml");
//    PictureService pictureService = (PictureService)ctx.getBean("PictureService");
//    AlbumService albumService = (AlbumService)ctx.getBean("AlbumService");
//    private ObjectMapper mapper = new ObjectMapper();
//    //存储当前相册
//    private static String img_id = null;
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if ("add".equals(req.getParameter("option"))){
//            addPicture(req,resp);
//        } else if ("delete".equals(req.getParameter("option"))){
//            deletePicture(req,resp);
//        } else if ("findAll".equals(req.getParameter("option"))){
//            findAll(req,resp);
//        } else if ("edit".equals(req.getParameter("option"))){
//            edit(req,resp);
//        } else if ("findNote".equals(req.getParameter("option"))){
//            findNote(req,resp);
//        } else if ("findNote2".equals(req.getParameter("option"))){
//            findNote2(req,resp);
//        }
////        else if ("list".equals(req.getParameter("option"))){
////            list(req,resp);
////        }
//    }
//
//    private void findNote2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String id = req.getParameter("id");
//        System.out.println(id);
//        Picture picture = new Picture();
//        picture.setId(Integer.valueOf(id));
//        Picture picture1 = pictureService.findById(picture);
//        String data = null;
//        String s1 = null;
//        if (picture1.getNote() == null) {
//            data = "[]";
//        } else {
//            data = picture1.getNote();
//            System.out.println(data);
//            s1 = data.replaceAll("\\\\", "");
//            System.out.println(s1);
//            data = s1.substring(9,s1.length()-2);
//            System.out.println(data);
//        }
//        resp.getWriter().write(data);
//    }
//
//    private void findNote(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String id = req.getParameter("id");
//        System.out.println(id);
//        Picture picture = new Picture();
//        picture.setId(Integer.valueOf(id));
//        Picture picture1 = pictureService.findById(picture);
//        String data = null;
//        String s1 = null;
//        if (picture1.getNote() == null) {
//            data = "[]";
//        } else {
//            data = picture1.getNote();
//            System.out.println(data);
//            s1 = data.replaceAll("\\\\", "");
//            System.out.println(s1);
////            data = s1.substring(9,s1.length()-2);
//            System.out.println(data);
//        }
//        resp.getWriter().write(data);
//    }
//
//    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String id = req.getParameter("img_id");
//        System.out.println(id);
//        HashMap<String,Object> map = new HashMap<>();
//        String contentType = req.getContentType();
//        if (contentType != null && !contentType.equals("")) {
//            contentType = contentType.split(";")[0];
//        }
//        if ("application/json".equalsIgnoreCase(contentType)){
//            String paramJson = IOUtils.toString(req.getInputStream(),"UTF-8");
//            System.out.println(paramJson);
//            Map parseObject = JSON.parseObject(paramJson, Map.class);
//            map.putAll(parseObject);
//        }
//        String data = JSON.toJSONString(map);
//        Picture picture = new Picture();
//        picture.setId(Integer.valueOf(id));
//        picture.setNote(data);
//        pictureService.update(picture);
//        resp.getWriter().write("收到");
//    }
//
//    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String album_id = req.getParameter("id");
//        img_id = album_id;
//        Picture picture = new Picture();
//        picture.setAlbum_id(Integer.valueOf(album_id));
//        List<Picture> all = pictureService.findAll(picture);
//        String json = mapper.writeValueAsString(all);
//        resp.getWriter().write(json);
////        resp.sendRedirect("index.html");
//    }
//
////    private void list(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
//////        String album = req.getParameter("album");
//////        Cookie cookie = new Cookie("album_id", album);
//////        resp.addCookie(cookie);
//////        resp.sendRedirect("index.html");
//////    }
//    private void deletePicture(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        Album album = new Album();
//        album.setId(Integer.valueOf(img_id));
//        String id = req.getParameter("id");
//        String name = req.getParameter("name");
//        System.out.println(1111111);
//        System.out.println(name);
//        System.out.println(2222222);
//        pictureService.delete(new Picture(Integer.valueOf(id), name));
//        System.out.println(new File(this.getServletContext().getRealPath("img")+"\\"+name).delete());
//        if (name.equals(albumService.findById(album).getDefault_img())){
//            System.out.println(333333);
//            Picture picture = new Picture();
//            picture.setAlbum_id(Integer.valueOf(img_id));
//            List<Picture> all = pictureService.findAll(picture);
//            if (all.size() == 0){
//                album.setDefault_img("empty.jpg");
//                albumService.update(album);
//            } else {
//                String PictureName = all.get(all.size() - 1).getName();
//                album.setDefault_img(PictureName);
//                albumService.update(album);
//            }
//        }
//        //resp.sendRedirect("index.html");
//    }
//
//    private void addPicture(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//        System.out.println(id);
//        //1.确认该操作是否支持文件上传,enctype="multipart/form-data
//        if (ServletFileUpload.isMultipartContent(req)){
//            //2.创建磁盘工厂对象
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//            //3.Servlet上传核心
//            ServletFileUpload fileUpload = new ServletFileUpload(factory);
//            //4.从request中读取数据
//            List<FileItem> fileItems = null;
//            try {
//                fileItems = fileUpload.parseRequest(req);
//            } catch (FileUploadException e) {
//                e.printStackTrace();
//            }
//
//            for (FileItem item : fileItems) {
//                //5.当前表单是否是文件表单
//                if (!item.isFormField()){
//                    //6.从临时存储文件的地方将内容写入指定位置
//                    String uuid = UUID.randomUUID().toString();
//                    try {
//                        item.write(new File(this.getServletContext().getRealPath("img"),uuid+item.getName()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    pictureService.save(new Picture(uuid+item.getName(),Integer.valueOf(img_id),new Date()));
//                    Album album = new Album();
//                    album.setId(Integer.valueOf(img_id));
//                    album.setDefault_img(uuid+item.getName());
//                    albumService.update(album);
//                }
//            }
//        }
////        resp.sendRedirect("index.html");
//    }
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doGet(req,resp);
//    }
//}
