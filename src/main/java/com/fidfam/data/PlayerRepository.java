package com.fidfam.data;

import com.fidfam.model.Player;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Josh on 6/23/17.
 *
 * Some Spring Boot Hibernate Magic implements this interface
 */
public interface PlayerRepository extends CrudRepository<Player, Long>{
}
