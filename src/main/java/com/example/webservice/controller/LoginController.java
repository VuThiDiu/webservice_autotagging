package com.example.webservice.controller;
import com.example.webservice.JwtTokenProvider;
import com.example.webservice.LoginResponse;
import com.example.webservice.dto.UserLogin;
import com.example.webservice.model.CustomUserDetails;
import com.example.webservice.model.User;
import com.example.webservice.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserService userService;
    @GetMapping(value = {"/login", "/", ""})
    public String getLoginForm( Model model, HttpServletRequest httpServletRequest){
        model.addAttribute("loginForm", new UserLogin());
        model.addAttribute("registerForm", new User());
        return "index";
    }


    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity authentication(@RequestBody UserLogin user,  HttpServletRequest request){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
            User user1 = (User) userService.getUserByUserName(user.getUsername());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            return  new ResponseEntity(new LoginResponse(jwt,  user1.getId(),user1.getUsername()), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.debug(e.toString());
        }
        return  new ResponseEntity(new LoginResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

