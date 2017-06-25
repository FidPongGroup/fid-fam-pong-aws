package com.fidfam.data;

import com.fidfam.model.GameTwoPlayer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Josh on 6/24/17.
 */
public interface TwoPlayerGameRepository extends CrudRepository<GameTwoPlayer, Long> {
}
