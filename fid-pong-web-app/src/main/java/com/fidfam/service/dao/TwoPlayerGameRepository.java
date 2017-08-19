package com.fidfam.service.dao;

import com.fidfam.model.domain.GameTwoPlayer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Josh on 6/24/17.
 */
public interface TwoPlayerGameRepository extends CrudRepository<GameTwoPlayer, Long> {
}
