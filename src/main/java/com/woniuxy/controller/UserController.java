package com.woniuxy.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.woniuxy.controller.form.*;


import com.woniuxy.dao.UserDao;
import com.woniuxy.model.City;
import com.woniuxy.model.Province;
import com.woniuxy.model.User;
import com.woniuxy.service.AddministService;
import com.woniuxy.service.UserService;
import com.woniuxy.service.iofo.AdministraIofo;
import com.woniuxy.service.iofo.GetUserByzhuyeIofo;
import com.woniuxy.service.iofo.UserSigninIofo;
import com.woniuxy.service.serviceImpl.AdministraTorsServiveImpl;
import com.woniuxy.service.serviceImpl.UserServiceImpl;
import com.woniuxy.utils.commons.*;
import com.woniuxy.utils.exception.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.annotations.Param;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;


@WebServlet("/user.do")
public class UserController extends BaseServlet {
    //事物控制
    UserService us = new JdkProxyUtil<UserService>().getProxyObject(new UserServiceImpl());

    //登录用户
    public void signin(HttpServletRequest request, HttpServletResponse response)throws IOException {

        //获取用户输入
        String uname = request.getParameter("uname");
        String upwd = request.getParameter("upwd");
        //将用户密码加密
        String md5 = Md5Util.getMd5(upwd);
        //IUHXwwUefpZvVqPAaNPT8w==
        UserSigninForm userSigninForm = new UserSigninForm(uname, md5, 0);


        //判断是否是管理员
        AdministratorsSigninForm administratorsSigninForm = new AdministratorsSigninForm(uname, upwd);
        AddministService add = new JdkProxyUtil<AddministService>().getProxyObject(new AdministraTorsServiveImpl());
        Result administrators = add.getAdministrators(administratorsSigninForm);
        if(null!=administrators){
            //登录的是管理员
            HttpSession session = request.getSession();
            session.setAttribute("admin",((AdministraIofo)administrators.getData()));
            response.getWriter().write(JSON.toJSONString(Result.success(8888,"管理员登陆成功",administrators)));

        }else{
            //用户登录
            try {
                Result user = us.getUser(userSigninForm);

                UserSigninIofo ss= (UserSigninIofo) user.getData();

                if(ss.getState()!=4){
                    //登录成功
                    HttpSession session = request.getSession();
                    session.setAttribute("user",user.getData());
                }


                response.getWriter().write(JSON.toJSONString(Result.success(200,"登陆成功",user)));
            } catch (Exception e) {
                response.getWriter().write(JSON.toJSONString(Result.fail(5900,"查询失败")));
            }
        }



    }
    //注册用户
    public String addUser(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String uname = request.getParameter("uname");
        String upwd = request.getParameter("upwd");
        String email=request.getParameter("email");
        String jihema=request.getParameter("str");
        //判断激活码是否错误
        HttpSession session = request.getSession();
        String email1 =(String) session.getAttribute("email");

        if(!email1.equals(jihema)){
            response.getWriter().write(JSON.toJSONString(Result.fail(5003,"邮箱验证码错误")));

        }else{
           //添加用户

            UserAddForm userAddForm = new UserAddForm(uname, upwd, email);

            //添加用户登录
            UserSigninForm userSigninForm = new UserSigninForm(uname, upwd, 0);

            try {
                Result result = us.addUser(userAddForm);

                Result result1 = us.addUserBySignin(userSigninForm);

                response.getWriter().write(JSON.toJSONString(Result.success(200,"注册成功","")));

            } catch (WoniuMallException e) {

                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }


        }


        return null;
    }

    //判断用户名是否重复
    public void exiStence(HttpServletRequest request,HttpServletResponse response) throws IOException{

        String uname = request.getParameter("uname");

        try {
            Result result = us.judgeUser(uname);

            response.getWriter().write(new JSONObject().toJSONString(Result.success(200,"用户名可以使用","")));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(new JSONObject().toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //发送邮箱
    public void getEmail(HttpServletRequest request,HttpServletResponse response) throws IOException{

        String email = request.getParameter("emails");

        //发送邮件
         EmailUtil s=new EmailUtil();
        String s1 = s.EmailUtil(email);
        //获取邮箱发送的验证码 存入Session
        HttpSession session = request.getSession();
        session.setAttribute("email",s1);
        response.getWriter().write(JSON.toJSONString(Result.success(200,"发送成功",s1)));


    }

    //判断邮箱是否正确
    public void judgeEmail(HttpServletRequest request,HttpServletResponse response){

        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        String email1 =(String) session.getAttribute("email");

        if(email.equals(email1)){
            try {
                response.getWriter().write(JSON.toJSONString("true"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //主页校验用户/管理员是否登录
    public void  signinHomepage(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //判断用户是否登录
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        Object admin = session.getAttribute("admin");
        if (null!=user){
            //用户已登录
            response.getWriter().write(JSON.toJSONString(Result.success(200,"用户已登录",user)));
        }else if(null!=admin){
            //管理员已登录
            response.getWriter().write(JSON.toJSONString(Result.success(8888,"管理员已登录",admin)));
        }else{
            //没有任何登录信息
            response.getWriter().write(JSON.toJSONString(Result.fail(4040,"无登录信息")));
        }


    }

    //获取用户主页展示信息
    public  void getUserByzhuye(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获取用户名
        String user = request.getParameter("user");
        GetUserByzhuyeForm getUserByzhuyeForm = new GetUserByzhuyeForm(user);

        try {
            Result userByzhuye = us.getUserByzhuye(getUserByzhuyeForm);

            response.getWriter().write(JSON.toJSONString(userByzhuye));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(4040,"没有查询到用户信息")));
        }

    }

    //退出登录
    public void delSession(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //清除当前所有Session
        HttpSession session = request.getSession();
        session.invalidate();
        response.getWriter().write(JSON.toJSONString(Result.success(200,"清除成功",null)));

    }

    //全局登录用户
    UserSigninIofo usessr;
    //用户发送激活邮件
    public void sendMail(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //获取登录对象
        HttpSession sessions = request.getSession();
        UserSigninIofo user =(UserSigninIofo) sessions.getAttribute("user");
        usessr=user;

        //通过用户名获取用户邮箱
        try {
            Result user1 = us.getUserByuname(user);

            User usc= (User) user1.getData();


            if(null!=usc){
                //调用方法
                Result result = us.ModifyActivation(usc);

                response.getWriter().write(JSON.toJSONString(result));
            }else {
                //用户没有登录就进入了激活界面 ？不是

            }

        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //激活用户
    public  String activationUser(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //获取用户对象
        try {
            Result user1 = us.getUserByuname(usessr);


            User usc= (User) user1.getData();

            Result result = us.ActivationUser(usc);

            //重定向
            if(null!=result)  response.sendRedirect("/html/frontpage/homepage.html");

        } catch (WoniuMallException e) {
            e.printStackTrace();

            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }
        return null;
    }

    //获取用户信息展示个人中心
    public void getUserPersonalCenter(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //判断用户是否登录
        HttpSession session = request.getSession();
        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");
        if(null!=user){
            try {
                Result userByuname = us.getUserByuname(user);
                response.getWriter().write(JSON.toJSONString(userByuname));
            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }
        }else{
            //用户没有登录 直接让他跳转登录界面
            response.getWriter().write(JSON.toJSONString(Result.fail(4058,"用户未登录")));
        }


    }

    //用户上传头像
    public  void addUserTouXiang(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //用于判断接收请求是否存在文件上传内容
        boolean multipartContent = ServletFileUpload.isMultipartContent(request);
        if(multipartContent){
            //需要创建servletFileUoload对象 应为后续需要使用
            ServletFileUpload Upload = new ServletFileUpload(new DiskFileItemFactory());
            //设置用户上传内容大小
            Upload.setSizeMax(20*1024*1024);//20m


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
                        System.out.println(fieldName+":"+value);

                    }else{
                        //做文件上传的处理
                        //上传之后，文件应该存储的位置？
                        String Path = this.getServletContext().getRealPath("/images");

                        //文件路径
                        System.out.println(Path);
                        //指定上传文件保存名称  （不能重名，否则会导致覆盖）
                        String saveName = UUID.randomUUID().toString();


                        //获取上传文件的原始名称
                        String name = fileItem.getName();
                        System.out.println(name);

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

                        //获取用户ID修改用户头像
                        HttpSession session1 = request.getSession();
                        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");

                        try {
                            Result result = us.updaUserTouxiang(user, imagepath);

                            response.sendRedirect("/html/frontpage/PersonalCenter.html");


                        } catch (WoniuMallException e) {
                            e.printStackTrace();
                            response.sendRedirect("/html/frontpage/PersonalCenter.html");
                        }

                    }
                }


            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //重定向
        response.sendRedirect("/html/frontpage/PersonalCenter.html");
    }

    //修改用户信息
    public void updaUserXinXi(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //待修改的密码
        String upwd = request.getParameter("upwd");
        //待修改的手机号
        String shouji = request.getParameter("shouji");
        //待修改的性别
        String selectR = request.getParameter("selectR");
        Integer integer = new Integer(selectR);
        String sex;
        if(integer==1){
            sex="男";
        }else{
            sex="女";
        }

        //待修改的生日
        String shengri = request.getParameter("shengri");

        //获取用户ID修改用户头像
        HttpSession session1 = request.getSession();
        UserSigninIofo user =(UserSigninIofo) session1.getAttribute("user");

        UserUpdaForm userUpdaForm = new UserUpdaForm(upwd, shouji, selectR, shengri);

        try {
            Result result = us.updaUserXingXi(userUpdaForm, user);
            //重定向
            response.getWriter().write(JSON.toJSONString(result));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            //重定向
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }

    }

    //获取用户所有地址信息
    public void getUserAddress(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //获取当前用户信息
        HttpSession session = request.getSession();
        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");
        try {
            Result userAddressAll = us.getUserAddressAll(user);
            response.getWriter().write(JSON.toJSONString(userAddressAll));
        } catch (WoniuMallException e) {
            e.printStackTrace();

            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //获取用户默认地址信息
    public void getUsermrenAddress(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");

        try {
            Result usermorenAddresss = us.getUsermorenAddresss(user);
            response.getWriter().write(JSON.toJSONString(usermorenAddresss));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }

    }

    //预加载省
    public void addsheng(HttpServletRequest request,HttpServletResponse response) throws IOException {

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);

        ArrayList<Province> province = mapper.getProvince();

        response.getWriter().write(JSON.toJSONString(Result.success(200,"省信息获取成功",province)));


    }

    //根据省ID获取市集合
    public void addshi(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String sid = request.getParameter("City");
        Integer id=new Integer(sid);

        UserDao mapper = MybatisUtil.getMapper(UserDao.class);
        ArrayList<City> city = mapper.getCity(id);

        response.getWriter().write(JSON.toJSONString(Result.success(200,"市查询成功",city)));

    }

    //用户添加新收货地址
    public  void addDiZhi(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //获取收件人姓名
        String sname = request.getParameter("sname");
        //获取收件人手机
        String sphon = request.getParameter("sphon");

        //获取省ID
        String sheng = request.getParameter("sheng");
        if(null!=sheng){
            Integer shengid = new Integer(sheng);
            //获取市ID
            String shi = request.getParameter("shi");
            if(null!=shi){
                Integer shiid = new Integer(shi);
                //获取详情地址
                String xiangin = request.getParameter("xianqin");

                //获取当前用户信息
                HttpSession session = request.getSession();
                UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");

                AddUserRessForm addUserRessForm = new AddUserRessForm(shengid, shiid, xiangin,sname,sphon);

                try {
                    Result result = us.addUserRess(user, addUserRessForm);

                    response.getWriter().write(JSON.toJSONString(result));
                } catch (WoniuMallException e) {
                    e.printStackTrace();
                    response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
                }
            }

        }

    }

    //删除用户地址
    public void delUserDizhi(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String aid = request.getParameter("aid");
        if(null!=aid){
            Integer integer = new Integer(aid);
            //获取当前用户ID
            HttpSession session = request.getSession();
            UserSigninIofo user = (UserSigninIofo) session.getAttribute("user");
            try {
                Result result = us.delUserRessById(user, integer);
                response.getWriter().write(JSON.toJSONString(result));
            } catch (WoniuMallException e) {
                e.printStackTrace();

                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }

        }

    }

    //用户更换默认地址
    public void upDateUserRess(HttpServletRequest request,HttpServletResponse response)throws  IOException{

        String aid = request.getParameter("aid");

        if(null!=aid){
            Integer integer = new Integer(aid);
            HttpSession session = request.getSession();
            UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");
            try {
                Result result = us.gengHuangRess(user, integer);
                response.getWriter().write(JSON.toJSONString(result));
            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }

        }

    }

    //用户全选结算
    public void jieSuanGoodsAll(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //调用方法 结算购物车表中的所有数据

        //获取当前在线用户对象

        HttpSession session = request.getSession();
        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");

        try {
            Result userShoPingByAll = us.getUserShoPingByAll(user);

            //存入订单session  订单编号
            session.setAttribute("shopping",userShoPingByAll.getData());

            response.getWriter().write(JSON.toJSONString(userShoPingByAll));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }
    }

    //用户单选结算
    public void  jieSuanGoods(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //获取用户需要提交的商品
        String gids = request.getParameter("arr");

        List<Integer> integers = JSONArray.parseArray(gids, Integer.class);


        //获取当前在线用户
        HttpSession session = request.getSession();
        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");

        try {
            Result userShoPingBy = us.getUserShoPingBy(user, integers);

            //存入订单session  订单编号
            session.setAttribute("shopping",userShoPingBy.getData());

            response.getWriter().write(JSON.toJSONString(userShoPingBy));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //获取当前订单信息
    public void  UserGetShoPingbianhao(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //判断当前用户的订单编号
        HttpSession session = request.getSession();
        //订单编号
        long shopping =(long) session.getAttribute("shopping");

        if(shopping>0){

            //获取用户信息
            UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");

            try {
                Result userShopPingByUidByUUid = us.getUserShopPingByUidByUUid(user, shopping);

                response.getWriter().write(JSON.toJSONString(userShopPingByUidByUUid));

            } catch (WoniuMallException e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));

            }

        }


    }

    //订单页面展示默认地址
    public void  getUserMorenRessoo(HttpServletRequest  request,HttpServletResponse  response) throws IOException {

        //获取当前用户
        HttpSession session = request.getSession();

        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");

        try {
            Result usermorenAddresss = us.getUsermorenAddresss(user);
            if(null!=usermorenAddresss.getData()){
                response.getWriter().write(JSON.toJSONString(usermorenAddresss));
            }else{
                System.out.println(">>>>>>>>>>>获取默认地址失败");
            }
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //订单页面展示非默认地址
    public void  getUserMorenRessooAll(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //获取当前用户
        HttpSession session = request.getSession();
        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");
        try {
            Result userAddressAll = us.getUserAddressAll(user);
            if(null!=userAddressAll){
                response.getWriter().write(JSON.toJSONString(userAddressAll));
            }
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }

    }

    //获取当前用户信息
    public void getUserBydingdan(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");

        try {
            Result userByuname = us.getUserByuname(user);
            response.getWriter().write(JSON.toJSONString(userByuname));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //获取用户订单信息
    public void  getUserBydingdanAllwu(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //获取当前用户
        HttpSession session = request.getSession();
        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");

        try {
            Result userDingDanAll = us.getUserDingDanAll(user);
            response.getWriter().write(JSON.toJSONString(userDingDanAll));
        } catch (WoniuMallException e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
        }


    }

    //获取当前订单信息
    public void  getUserByDingdandanyi(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String sdid = request.getParameter("sdid");
        HttpSession session = request.getSession();
        UserSigninIofo user =(UserSigninIofo) session.getAttribute("user");
        if(null!=sdid){
            Integer integer = new Integer(sdid);

            try {
                Result userDingDandanyi = us.getUserDingDandanyi(user, integer);
                response.getWriter().write(JSON.toJSONString(userDingDandanyi));
            } catch (WoniuMallException e) {
                e.printStackTrace();

                response.getWriter().write(JSON.toJSONString(Result.fail(e.getCode(),e.getMessage())));
            }

        }


    }
}
