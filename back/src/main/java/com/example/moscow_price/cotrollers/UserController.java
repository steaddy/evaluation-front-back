package com.example.moscow_price.cotrollers;

import com.example.moscow_price.Entity.UserEntity;
import com.example.moscow_price.servis.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public String registration(@RequestBody UserEntity userEntity){
        userService.registration(userEntity);
        return "ok";
    }

    @PostMapping("/signIn")
    public String login (@RequestBody UserEntity userEntity){
        String s = userService.signIn(userEntity);
        String name = userEntity.getName();
        String password = userEntity.getPassword();
        System.out.println(name + " " + password);
        return s;
    }

}
