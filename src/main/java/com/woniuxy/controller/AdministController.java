package com.woniuxy.controller;

import com.alibaba.fastjson.JSON;
import com.woniuxy.controller.form.AddGoodsAdministForm;
import com.woniuxy.controller.form.XiuGaiGoodsAdministForm;
import com.woniuxy.dao.AdministratorsDao;
import com.woniuxy.service.AddministService;
import com.woniuxy.service.serviceImpl.AdministraTorsServiveImpl;
import com.woniuxy.utils.commons.*;
import com.woniuxy.utils.exception.WoniuMallException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.List;
import java.util.UUID;


@WebServlet("/admin.do")
public class AdministController extends BaseServlet {

    //事物控制
    AddministService as = new JdkProxyUtil<AddministService>().getProxyObject(new AdministraTorsServiveImpl());

    //获取所有商品分类信息
    public void getGoodsAdminist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Result goodsAdministratorsFenlei = as.getGoodsAdministratorsFenlei();
            response.getWriter().write(JSON.toJSONString(goodsAdministratorsFenlei));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //获取所有商品信息
    public void getGoodsAdministAll(HttpServletRequest request,HttpServletResponse response) throws IOException {

        try {
            Result goodsAdministratorsAll = as.getGoodsAdministratorsAll();

            response.getWriter().write(JSON.toJSONString(goodsAdministratorsAll));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //获取所有用户信息
    public void getUserAdministAll(HttpServletRequest request,HttpServletResponse response)throws IOException{
        try {
            Result userAdministrators = as.getUserAdministrators();
            response.getWriter().write(JSON.toJSONString(userAdministrators));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }

    }


    //添加商品
    public void addGoodsAdminist(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //用于判断接收请求是否存在文件上传内容
        boolean multipartContent = ServletFileUpload.isMultipartContent(request);
        if(multipartContent){
            //需要创建servletFileUoload对象 应为后续需要使用
            ServletFileUpload Upload = new ServletFileUpload(new DiskFileItemFactory());
            //设置用户上传内容大小
            Upload.setSizeMax(20*1024*1024);//20m


            AddGoodsAdministForm addgoods = new AddGoodsAdministForm();

            try {
                //解析请求 获取封装出来的fileitem集合 每一个FileItem对应一个表单控件的值
                List<FileItem> fileItems = Upload.parseRequest(request);

                for(FileItem fileItem:fileItems){
                    //判断是否是普通的表单字段
                    if(fileItem.isFormField()){

                        //获取表单控件的name属性值
                        String fieldName = fileItem.getFieldName();
                        //获取表单属性的value属性值
                        String value = fileItem.getString("utf-8");
                        //处理普通属性

                        //获取商品名
                        if(fieldName.equals("gname")){
                            addgoods.setGname(value);
                        }

                        //获取商品价格
                        if(fieldName.equals("gprice")){
                            Double aDouble = new Double(value);
                            addgoods.setGprive(aDouble);
                        }

                        //获取商品数量
                        if(fieldName.equals("gnum")){
                            Integer integer = new Integer(value);
                            addgoods.setGnum(integer);
                        }

                        //获取商品简介
                        if(fieldName.equals("jianji")){
                            addgoods.setGdescribe(value);
                        }

                        //获取商品类别ID
                        if(fieldName.equals("goodsleibie")){
                            Integer integer = new Integer(value);
                            addgoods.setGcategory(integer);
                        }



                    }else{
                        //做文件上传的处理
                        //上传之后，文件应该存储的位置？
                        String Path = this.getServletContext().getRealPath("/images");

                        //文件路径
    /*                    System.out.println(Path);*/
                        //指定上传文件保存名称  （不能重名，否则会导致覆盖）
                        String saveName = UUID.randomUUID().toString();


                        //获取上传文件的原始名称
                        String name = fileItem.getName();
                   /*     System.out.println(name);*/

                        String seveFileName=saveName+name.substring(name.lastIndexOf("."));

                        //创建保存上传文件内容的文件
                        File file = new File(Path, seveFileName);

                        //将文件内容写入到某个文件中
                        try {
                            fileItem.write(file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //删除由于文件过大而产生的临时文件
                        fileItem.delete();
                        HttpSession session = request.getSession();
                        //图片路径
                        session.setAttribute("imagepath","/images/"+seveFileName);
                        String imagepath =(String) session.getAttribute("imagepath");
                        System.out.println("图片路径："+imagepath);
                        //获取图片路径
                        addgoods.setImg(imagepath);

                    }

                }

                //添加数据库
                try {
                    Result result = as.addGoodsAdministrators(addgoods);

                    response.getWriter().write(JSON.toJSONString(result));
                } catch (WoniuMallException e) {
                    e.printStackTrace();
                    response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
                }



            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
       /* //重定向
        response.sendRedirect("/html/frontpage/PersonalCenter.html");*/
    }


    //下架商品
    public void delGoodsAdminist(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String gid = request.getParameter("gid");

        if(null!=gid){
            Integer integer = new Integer(gid);
            try {
                Result result = as.delGoodsAdministrators(integer);
                response.getWriter().write(JSON.toJSONString(result));
            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }
        }


    }



    //修改商品属性
    public void xiugaiGoods(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String gnames = request.getParameter("gnames");
        String gnums = request.getParameter("gnums");
        String gprives = request.getParameter("gprives");
        //商品ID
        String gid = request.getParameter("gid");
        Integer integer1 = new Integer(gid);
        if(null!=gnames&&null!=gnums&&null!=gprives){
            //获取修改得数量
            Integer integer = new Integer(gnums);
            //获取修改得价格
            Double aDouble = new Double(gprives);
            XiuGaiGoodsAdministForm xiuGaiGoodsAdministForm = new XiuGaiGoodsAdministForm(gnames, integer, aDouble);


            try {
                Result result = as.xiugaiGoodsAll(xiuGaiGoodsAdministForm,integer1);
                response.sendRedirect("html/frontpage/Administratorinterface.html");
            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.sendRedirect("html/frontpage/Administratorinterface.html");
            }
        }


    }


    //禁用用户
    public void  jingYong(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String uid = request.getParameter("uid");
        if(null!=uid){
            Integer integer = new Integer(uid);

            try {
                Result result = as.jingyongUserAdministrators(integer);

                response.getWriter().write(JSON.toJSONString(result));

            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }


        }

    }


    //解禁用户
    public void jieJing(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String uid = request.getParameter("uid");

        if(null!=uid){
            Integer integer = new Integer(uid);

            try {
                Result result = as.jieJing(integer);

                response.getWriter().write(JSON.toJSONString(result));
            } catch (WoniuMallException e) {
                e.printStackTrace();

                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }
        }
    }

    //获取所有的管理员对象
    public void getAdminist(HttpServletRequest request,HttpServletResponse response) throws IOException {

        try {
            Result adminstraTo = as.getAdminstraTo();

            response.getWriter().write(JSON.toJSONString(adminstraTo));
        } catch (WoniuMallException e) {
            e.printStackTrace();

            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }

    }

    //修改管理员密码
    public void updataAdministPwd(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String jpwd = request.getParameter("jpwd");
        String xpwd = request.getParameter("xpwd");
        String id = request.getParameter("id");
        if(null!=jpwd&&null!=xpwd){
            Integer integer = new Integer(id);
            try {
                Result result = as.upDataAdministrators(jpwd, xpwd, integer);
                response.getWriter().write(JSON.toJSONString(result));
            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }


        }
    }

    //获取所有用户订单信息
    public void getUserAllAdminist(HttpServletRequest request,HttpServletResponse response) throws IOException {


        try {
            Result showUserShopPingDetails = as.getShowUserShopPingDetails();

            response.getWriter().write(JSON.toJSONString(showUserShopPingDetails));

        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }


}
