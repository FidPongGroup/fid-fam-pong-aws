package com.fidfam.controller;

import com.fidfam.service.dao.TwoPlayerGameRepository;
import com.fidfam.model.domain.GameTwoPlayer;
import com.fidfam.model.request.CreateTwoPlayerGameRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * Created by Josh on 6/24/17.
 *
 * Everybody's favorite Spring Boot Style REST Controller
 *
 * Returns games
 *
 */
@RestController
@RequestMapping("games/twoPlayer")
@Getter
@Setter
public class TwoPlayerGameController implements GameRestController<GameTwoPlayer, CreateTwoPlayerGameRequest>{

    @Autowired
    private TwoPlayerGameRepository repo;


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @Override
    public GameTwoPlayer getGame(@PathVariable @NotNull Long id) {
        return repo.findOne(id);
    }

    @RequestMapping("/")
    @Override
    public Iterable<GameTwoPlayer> getGames() {
        return repo.findAll();
    }
    @RequestMapping(path = "/",
        method = RequestMethod.POST)
    @Override
    public GameTwoPlayer createGame(@RequestBody @NotNull CreateTwoPlayerGameRequest request) {
        return null;
    }

    @Override
    public GameTwoPlayer increaseScore(Long gameId, Long playerId) {
        return null;
    }
}
