package com.example.webservice.controller;
import com.example.webservice.model.User;
import com.example.webservice.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;
    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerForm", new User());
        return "register";
    }



    @ResponseBody
    @PostMapping(value = "/register")
    public User Register(@RequestBody User user){
        String userName = user.getUsername();
        if(userName!=null && userName.length()>0){
            userService.createNewUser(user);
        }
        return user;
    }
}
