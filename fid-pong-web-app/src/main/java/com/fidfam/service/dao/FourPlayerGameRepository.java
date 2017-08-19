package com.fidfam.service.dao;

import com.fidfam.model.domain.GameFourPlayer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Josh on 7/16/17.
 */
public interface FourPlayerGameRepository extends CrudRepository<GameFourPlayer, Long>{
}
