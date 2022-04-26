package com.example.webservice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auto_tagging")
public class HomeController {

    private static String url = "https://e8ae-2402-800-61ae-c2f8-b849-7f71-b0a7-3e6c.ngrok.io/";
    @GetMapping("/home")
    public String getHome(Model model){
        model.addAttribute("url" , url + "auto_tagging/autoTaggingImage");
        return "home";
    }

}
