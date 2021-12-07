package edu.soft1.controller;


import edu.soft1.pojo.User;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
@RequestMapping("/param1")
public class MyController {
    @RequestMapping(value = "upload",method = {RequestMethod.POST})
    public String fileUpload(MultipartFile image,HttpServletRequest request) throws IOException {
        InputStream is = image.getInputStream();//输入流
        String filenname = image.getOriginalFilename();//文件名称
        String realPath = request.getServletContext().getRealPath("/images");
        System.out.println("上传路径="+realPath);
        FileOutputStream os = new FileOutputStream(new File(realPath,doFileName(filenname)));
        int size = IOUtils.copy(is,os);
        System.out.println("完成上传size="+size+"Byte");
        os.close();is.close();//关闭流
        return "welcome";
    }
    private String doFileName(String filenname){
        String extension = FilenameUtils.getExtension(filenname);//获取文件的后缀名称
        String uuid = UUID.randomUUID().toString();
        System.out.println("上传文件="+uuid);
        return uuid+"."+extension;
    }
    @RequestMapping(value = "upload2",method = {RequestMethod.POST})
    public String fileUpload2(MultipartFile[] images,HttpServletRequest request) throws IOException {
        for (MultipartFile image:images) {
            InputStream is = image.getInputStream();//输入流
            String filenname = image.getOriginalFilename();//文件名称
            String realPath = request.getServletContext().getRealPath("/images");
            System.out.println("上传路径="+realPath);
            FileOutputStream os = new FileOutputStream(new File(realPath,doFileName(filenname)));
            int size = IOUtils.copy(is,os);
            System.out.println("完成上传size="+size+"Byte");
            os.close();is.close();//关闭流
        }
        return "welcome";
    }
//    @RequestMapping("/hello.do")
//    public String hello(){
//        System.out.println("---hello()---");//进入方法的标志
//        return "hello";
//    }
//    @RequestMapping("/hello")
//    public ModelAndView hello(){
//        System.out.println("---hello()---");//进入方法的标志
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("hello");
//        mav.addObject("msg","I'm Peter");
//        return mav;
//    }
//    @RequestMapping("/hello")
//    public String hello(String username, Model model){
//        System.out.println("---hello()---");//进入方法的标志
//        model.addAttribute("username",username);
//        return "hello";
//    }
//@RequestMapping("/hello")
//    public String hello(String username, Model model){
//        System.out.println("---hello()---");//进入方法的标志
//        map.put("username",username);
//        return "hello";
//    }
    @RequestMapping(value = "/param1",method = {RequestMethod.GET})
    public String param1(HttpServletRequest request){
        //接收client发来的数据
        String name = request.getParameter("name");//获取数据
        System.out.println("name="+name);//打印数据
        request.setAttribute("name",name);//将数据存入request
        //调用业务层的ff
        //页面跳转
        return "hello";
    }

    @RequestMapping(value = "/param2",method = {RequestMethod.GET,RequestMethod.POST})
    public String param2(HttpServletRequest request, HttpSession session){
        //接收client发来的数据
        String name = request.getParameter("username");//获取数据
        String age = request.getParameter("age");//获取数据
        System.out.println("name="+name+",age="+age);//打印数据
        session.setAttribute("age",age);//将数据存入session
        request.setAttribute("name",name);//将数据存入request
        //调用业务层的ff
        //页面跳转
        return "hello";//跳转至jsp/hello.jsp页面
    }
    @RequestMapping(value = "param3",method = {RequestMethod.POST})
    public String param3(String username,int age){//数据名与方法参数名不同
        System.out.println("----param3()----");
        System.out.println("username="+username);
        System.out.println("age="+age);
        return "hello";
    }
    @RequestMapping(value = "param4",method = {RequestMethod.POST})//数据名与方法参数名不同
    public String param4(
            @RequestParam(value = "username",required = false) String u,
            @RequestParam(value = "age",defaultValue = "18") int a,HttpSession session){
        System.out.println("----param4()----");
        System.out.println("u="+u);
        System.out.println("a="+a);
        session.setAttribute("name",u);
        return "redirect:test";
    }
    @RequestMapping(value = "param5",method = {RequestMethod.POST})//数据名与方法参数名不同
    public String param5(User user,HttpSession session){
        System.out.println("----param5()----");
        System.out.println("username="+user.getUsername());
        System.out.println("age="+user.getAge());
        session.setAttribute("name",user.getUsername());
        return "redirect:test";//url:/WEB-INF/jsp/hello.jsp(默认为转发跳转)
    }
    @RequestMapping("test")//test.do
    public String text(){
        System.out.println("---text()---");
        return "hello";
    }
    @RequestMapping("/reg")
    public String reg(User user){
        System.out.println("username="+user.getUsername());
        System.out.println("pwd="+user.getAge());
        System.out.println("birthday="+user.getBirthday());
        System.out.println("city="+user.getAddress().getCity());
        System.out.println("street="+user.getAddress().getStreet());
        System.out.println("phone="+user.getAddress().getPhone());
        return "redirect:test";
    }
}
