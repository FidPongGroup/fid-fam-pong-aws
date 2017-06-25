package com.fidfam.controller;

import com.fidfam.data.PlayerRepository;
import com.fidfam.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Josh on 4/28/17.
 *
 * Spring Boot Rest Controller, currently returns a list of Players
 */
@RestController
public class PlayerController {

    @Autowired
    public PlayerRepository repository;

    @RequestMapping("/player")
    public Iterable<Player> index(){
        return repository.findAll();
    }
}
