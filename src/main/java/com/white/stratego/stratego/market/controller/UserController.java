package com.white.stratego.stratego.market.controller;
import com.white.stratego.stratego.market.service.UserServiceImpl;
import com.white.stratego.stratego.market.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.white.stratego.stratego.market.model.User;
import com.white.stratego.stratego.market.service.SecurityService;
import com.white.stratego.stratego.market.validator.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class UserController {
    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private UserServiceImpl userService;

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
    public String signup(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors()) {
            ArrayList<String> errors = new ArrayList<>();
            for(Object e : bindingResult.getAllErrors()) {
                String field = ((FieldError)e).getField();
                if(field.equals("email")) {
                    errors.add("Email already exist.");
                }
                if(field.equals("password")) {
                    errors.add("Password length must between 8 and 32.");
                }
                if(field.equals("passwordConfirm")) {
                    errors.add("Password doesn't match.");
                }
            }
            model.addAttribute("errors", errors);
            return "signup";
        }

        userService.save(userForm);

        verificationTokenService.createVerification(userForm.getEmail());

        return "redirect:/verify";
    }

    @RequestMapping("/login")
    public String login(Model model, String error, String logout, Authentication authentication) {
        if(authentication != null && authentication.isAuthenticated())

            return "redirect:/dashboard";
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }


        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }


        return "login";
    }

    @RequestMapping("/verify")
    public String sendVerify(Authentication authentication) {

        return "verify";
    }

    @RequestMapping("/verify/{token}")
    public String verifyEmail(@PathVariable String token) {
        ResponseEntity<String> response = verificationTokenService.verifyEmail(token);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/verifySuccess";
        }
        else {
            return "redirect:/verify";
        }
    }

    @RequestMapping("/try")
    public String try1() {
        return "game";
    }

    @RequestMapping("/verifySuccess")
    public String verifySuccess(){
        return "verifySuccess";
    }

    @PostMapping("/verifyResend")
    @ResponseBody
    public HashMap<String, String> resend(@RequestParam String email) {
        HashMap<String, String> map = new HashMap<>();
        User u = userService.findByEmail(email);
        if(u == null) {
            map.put("message", "notExist");
        } else if(u.getIsActive()) {
            map.put("message", "active");
        } else {
            verificationTokenService.createVerification(email);
            map.put("message", "success");
        }
        return map;
    }
}
