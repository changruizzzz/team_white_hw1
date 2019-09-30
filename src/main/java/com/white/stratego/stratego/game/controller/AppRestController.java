package com.white.stratego.stratego.game.controller;

import com.white.stratego.stratego.game.Model.Game;
import com.white.stratego.stratego.game.repository.GameRepository;
import com.white.stratego.stratego.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppRestController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    @RequestMapping("/api/users")
    public Object getUsers() {
        return userRepository.findAll();
    }
    @RequestMapping("/api/games/{id}")
    public Object getGame(@PathVariable long id) {
        Game currentGame = gameRepository.findById(id);
        return currentGame;
    }
    @RequestMapping("/api/games/{id}/current")
    public Object getGameCurrent(@PathVariable long id) {
        Game currentGame = gameRepository.findById(id);
        return currentGame.getBoard();
    }

    @RequestMapping("/api/games/{id}/initial")
    public Object getGameInitial(@PathVariable long id) {
        Game currentGame = gameRepository.findById(id);
        return currentGame.getInitialBoard();
    }

    @RequestMapping("/api/games/{id}/moves")
    public Object getMoves(@PathVariable long id) {
        Game currentGame = gameRepository.findById(id);
        return currentGame.getMoves();
    }
}
