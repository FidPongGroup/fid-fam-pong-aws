package com.fidfam.service.dao;

import com.fidfam.model.player.Player;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Josh on 6/23/17.
 *
 * Some Spring Boot Hibernate Magic implements this interface
 */
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {
}
