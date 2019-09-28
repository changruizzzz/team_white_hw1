package com.white.stratego.stratego.game.controller;

import com.white.stratego.stratego.game.*;
import com.white.stratego.stratego.game.repository.BoardRepository;
import com.white.stratego.stratego.game.repository.GameRepository;
import com.white.stratego.stratego.game.repository.StatisticsRepository;
import com.white.stratego.stratego.game.service.GameService;
import com.white.stratego.stratego.market.model.User;
import com.white.stratego.stratego.market.repository.UserRepository;
import com.white.stratego.stratego.market.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardRepository boardRepository;

    @RequestMapping("/create")
    public String newGame(Authentication authentication) {
        Game g = new Game();
        gameService.gameStart(g);
        //Save here?
        System.err.println(g.getBoard());
        System.out.println(boardRepository);
        System.err.println(g.getInitialBoard());
        boardRepository.save(g.getInitialBoard());
        boardRepository.save(g.getBoard());
        g.setIf_public(false);

        User user = userService.findByAuthentication(authentication);
        g.setCreatedBy(user);
        gameRepository.save(g);

        return "redirect:/dashboard";
    }

    @RequestMapping("/delete/{id}")
    public String newGame(@PathVariable long id) {
        gameRepository.deleteById(id);

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

        User user = userService.findByAuthentication(authentication);
        if(!user.getIsActive())
            return "redirect:/verify";
        Set<Game> games = gameRepository.findByCreatedBy(user);
        Statistics stats = statisticsRepository.findByUser(user);
        if(stats == null) {
            stats = new Statistics();
            stats.setUser(user);
            statisticsRepository.save(stats);
        }
        model.addAttribute("games", games);
        model.addAttribute("user", user);
        return "dashboard";
    }

    @PostMapping("/game/{id}/processMove")
    @ResponseBody
    public MoveResponse processMove(@RequestBody MoveRequest request, @PathVariable long id) {

        Game game = gameRepository.findById(id);
        
        return new MoveResponse();

    }

    @PostMapping("/game/{id}/askMove")
    @ResponseBody
    public MoveResponse askMove(@PathVariable long id) {
        Game game = gameRepository.findById(id);
        return new MoveResponse();

    }

}
