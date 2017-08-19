package com.fidfam.controller;

import com.fidfam.model.player.AbstractPlayer;
import com.fidfam.model.player.CreatePlayerRequest;
import com.fidfam.model.player.UpdatePlayerRequest;
import com.fidfam.service.PlayerService;
import com.fidfam.service.dao.PlayerRepository;
import com.fidfam.model.player.Player;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Josh on 4/28/17.
 *
 * Spring Boot Rest Controller, currently returns a list of Players
 */
@RestController
@RequestMapping("/player")
@Getter
@Setter
public class PlayerRestController {

    @Autowired
    private PlayerService service;

    @RequestMapping("/")
    public Iterable<Player> getPlayers(){
        return service.getPlayers();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Player getPlayer(@PathVariable("id") Long id){
        return service.getPlayer(id);
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Player createPlayer(@RequestBody CreatePlayerRequest request){
        Player p = buildPlayer(request);
        return service.createPlayer(p);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PATCH)
    public Player updatePlayer(@PathVariable Long id, UpdatePlayerRequest request){
        Player p = buildPlayer(request);
        p.setId(id);
        return service.updatePlayer(p);
    }

    private Player buildPlayer(AbstractPlayer ap){
        Player p = new Player();
        p.setFirstName(ap.getFirstName());
        p.setLastName(ap.getLastName());
        p.setEmail(ap.getEmail());
        p.setNickname(ap.getNickname());
        return p;
    }
}
