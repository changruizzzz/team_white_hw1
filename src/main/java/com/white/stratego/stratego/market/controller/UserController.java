package com.white.stratego.stratego.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.white.stratego.stratego.market.model.User;
import com.white.stratego.stratego.market.service.SecurityService;
import com.white.stratego.stratego.market.service.UserService;
import com.white.stratego.stratego.market.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userForm", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {

        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors()) {
            return "signup";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/dashboard";
    }

    @RequestMapping("/login")
    public String login(Model model, String error, String logout, Authentication authentication) {
        System.err.println("in");
        if(authentication != null && authentication.isAuthenticated())

            return "redirect:/dashboard";
        if (error != null) {
            System.err.println("11");
            model.addAttribute("error", "Your username and password is invalid.");
        }


        if (logout != null) {
            System.err.println("22");
            model.addAttribute("message", "You have been logged out successfully.");
        }


        return "login";
    }

}
