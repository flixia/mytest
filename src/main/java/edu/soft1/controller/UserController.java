package edu.soft1.controller;

import edu.soft1.pojo.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @RequestMapping(value = "upload",method = {RequestMethod.POST})
    public String fileUpload(MultipartFile image, HttpServletRequest request) throws IOException {
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

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("---hello()---");//进入方法的标志
        return "hello";
    }

    @RequestMapping(value = "/login")
    public String login(User user, HttpSession session, HttpServletRequest request){
       System.out.println("----login()----");//进入控制器的方法
        //获取username的值
        // request.getParameter("");//对get以及post的提交均生效
       int flag = 1;//调用业务层（业务层调用ndao层），获取flag的值
       if (flag == 1) {
           System.out.println("username="+user.getUsername());
//             session.setMaxInactiveInterval(10);
           session.setAttribute("user",user);//登录对象（名值对的方式）放入session
           return "welcome";//登录成功
       }
       System.out.println("登录失败，返回首页index");
       request.setAttribute("error","登录失败，请重新尝试");
        return "forward:/index.jsp";//登录失败
    }

    @RequestMapping("/logout")//登出功能
    public String logout(HttpSession session){
        //清空session
        session.invalidate();
        System.out.println("已登录~");
        return "redirect:/index.jsp";//重定向跳转至首页
    }

    @RequestMapping(value = "/upload.do",method = {RequestMethod.POST})
    public String fileUpload(MultipartFile images,HttpServletRequest request,Map<String,Object> map) throws IOException{
        InputStream is = images.getInputStream();
        OutputStream os = null;
        for (MultipartFile image :images) {
            is = image.getInputStream();
            String filename=getInputStream();
        if (size > 0) {
            map.put("msg","uploadSuccess");
            System.out.println("上传文件成功，");
        }
        }
        return "welcome";
    }
//    @RequestMapping(value = "/upload.do",method = {RequestMethod.POST})
    public String fileUpload2(MultipartFile[] images,HttpServletRequest request) throws IOException{
        InputStream is = null; OutputStream os = null;
        int count = 0;//计数器
        for (MultipartFile image :images) {
            is = image.getInputStream();
            String filename = image.getOriginalFilename();
            if (filename.equals("")) {
                continue;}//进入下一轮循环
            String realPath = request.getServletContext();
            System.out.println("上传路径realPath="+realPath);
            os = new FileOutputStream(new File(realPath,));
            int size = IOUtils.copy(is,os);
            if (size > 0) {count++;}
        }
        os.close();is.close();
        map.put("msg2",count);
        System.out.println("上传成功"+count+"张图片！");
        return "welcome";
    }

    @RequestMapping(value = "")
    public void load(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response)throws IOException{
        //设置文件下载
        response.setHeader("Content-Disposition","attachment;filename="+doFileName2(response));
    }
}
