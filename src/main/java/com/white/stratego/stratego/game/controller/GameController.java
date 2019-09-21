package com.white.stratego.stratego.game.controller;

import com.white.stratego.stratego.game.Game;
import com.white.stratego.stratego.market.model.MarketUnit;
import com.white.stratego.stratego.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.white.stratego.stratego.market.repository.MarketUnitRepository;
import com.white.stratego.stratego.market.model.User;

import java.util.Set;

@Controller
public class GameController {

    @Autowired
    private MarketUnitRepository marketUnitRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/create")
    public String newGame(Authentication authentication) {
        Game g = new Game();
        g.setIf_public(false);
        User user = userRepository.findByUsername(authentication.getName());
        g.setUser(user);
        marketUnitRepository.save((MarketUnit)g);

        return "redirect:/dashboard";
    }

    @RequestMapping("/delete/{id}")
    public String newGame(@PathVariable long id) {
        System.err.println("???");
        marketUnitRepository.deleteById(id);

        return "redirect:/dashboard";
    }

    @GetMapping({"/"})
    public String index(Authentication authentication) {
        if(authentication != null && authentication.isAuthenticated())
            return "redirect:/dashboard";
        return "index";
    }

    @RequestMapping({"/dashboard"})
    public String dashboard(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName());
        Set<MarketUnit> games = marketUnitRepository.findByCreatedBy(user);
        model.addAttribute("games", games);
        return "dashboard";
    }

}
