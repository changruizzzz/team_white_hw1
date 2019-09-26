package com.white.stratego.stratego.game.controller;

import com.white.stratego.stratego.market.model.User;
import com.white.stratego.stratego.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.List;
@RestController
public class AppRestController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/api/users")
    public Object getUsers() {
        System.err.println("Fetching user data!");
        return userRepository.findAll();
    }
}
