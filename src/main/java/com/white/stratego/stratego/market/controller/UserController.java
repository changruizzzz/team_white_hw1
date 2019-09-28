package com.white.stratego.stratego.market.controller;

import com.white.stratego.stratego.market.service.UserServiceImpl;
import com.white.stratego.stratego.market.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.white.stratego.stratego.market.model.User;
import com.white.stratego.stratego.market.service.SecurityService;
import com.white.stratego.stratego.market.validator.UserValidator;

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
            System.err.println(bindingResult);
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
//        User user = userService.findByAuthentication(authentication);
//        String email = user.getEmail();
//        System.err.println(email);

        return "verifySendEmail";
    }

    @RequestMapping("/verify/{token}")
    public String verifyEmail(@PathVariable String token) {
        System.err.println("ok");
        ResponseEntity<String> response = verificationTokenService.verifyEmail(token);
        if (response.getStatusCode().is2xxSuccessful()) {
            System.err.println("1");
            User user = userService.findByToken(token);
            System.err.println("user");
            return "verifySuccess";
        }
        else {
            System.err.println(2);
            return "verify";
        }
    }

    @RequestMapping("/try")
    public String try1() {
        return "try";
    }

    @RequestMapping("/verifySuccess")
    public String verifySuccess(){
        return "verifySuccess";
    }

    @RequestMapping("/verifySendEmail")
    public String verifySendEmail(){
        return "verifySendEmail";
    }
}
