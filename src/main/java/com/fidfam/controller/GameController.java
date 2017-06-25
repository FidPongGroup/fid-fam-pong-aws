package com.fidfam.controller;

import com.fidfam.data.TwoPlayerGameRepository;
import com.fidfam.model.GameTwoPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Josh on 6/24/17.
 *
 * Everybody's favorite Spring Boot Style REST Controller
 *
 * Returns games
 *
 */
@RestController
public class GameController {

    @Autowired
    public TwoPlayerGameRepository repository;

    @RequestMapping("/games")
    public Iterable<GameTwoPlayer> getGames(){
        return repository.findAll();
    }
}
