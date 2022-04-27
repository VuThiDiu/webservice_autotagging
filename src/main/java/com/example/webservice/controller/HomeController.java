package com.example.webservice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auto_tagging")
public class HomeController {
    private static String url = "localhost:/8080/";
    @GetMapping("/home")
    public String getHome(Model model){
        model.addAttribute("url" , url + "auto_tagging/autoTaggingImage");
        return "home";
    }

}
