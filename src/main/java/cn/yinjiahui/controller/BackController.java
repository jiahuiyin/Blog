package cn.yinjiahui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BackController {





    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }


    @GetMapping("/aboutme")
    public String aboutme(){
        return "aboutme";
    }


    @GetMapping("/update")
    public String update(){

        return "update";
    }



    @GetMapping("/user")
    public String user(){
        return "user";
    }

    /**
     * 跳转到文章编辑页
     */
    @GetMapping("/editor")
    public String editor(){
        return "editor";
    }


    @GetMapping("/archives")
    public String archives(){
        return "archives";
    }


    @GetMapping("/categories")
    public String categories(){
        return "categories";
    }

    /**
     * 跳转到标签页
     */
    @GetMapping("/tags")
    public String tags(){

        return "tags";
    }

    @GetMapping("/search")
    public String search(){
        return "search";
    }

    /**
     * 跳转到超级管理员页
     */
    @GetMapping("/superadmin")
    public String superadmin(HttpServletRequest request){
        return "superadmin";
    }




    @GetMapping("/reward")
    public String reward(HttpServletRequest request){
        return "reward";
    }



    @GetMapping("/article/*")
    public String show() {
        return "show";
    }
}
