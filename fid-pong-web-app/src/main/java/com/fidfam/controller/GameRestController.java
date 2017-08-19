package com.fidfam.controller;

import com.fidfam.model.domain.Game;
import com.fidfam.model.request.CreateGameRequest;

/**
 * Created by Josh on 7/17/17.
 */
public interface GameRestController<T extends Game, A extends CreateGameRequest> {

    public T getGame(Long id);

    public Iterable<T> getGames();

    public T createGame(A request);

    public T increaseScore(Long gameId, Long playerId);



}
