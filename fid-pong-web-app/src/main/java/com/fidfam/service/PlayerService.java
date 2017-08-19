package com.fidfam.service;

import com.fidfam.exceptions.player.PlayerNotFoundException;
import com.fidfam.exceptions.player.SavePlayerException;
import com.fidfam.model.player.Player;
import com.fidfam.model.player.CreatePlayerRequest;
import com.fidfam.service.dao.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Created by Josh on 7/18/17.
 */
@Slf4j
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository repo;

    public Player createPlayer(@NotNull Player p){

        try{
            return repo.save(p);
        }catch(DataIntegrityViolationException e){
            throw new SavePlayerException(e);
        }catch(Exception e){
            throw new SavePlayerException("Error Saving Player Data", e);
        }

    }

    public Player getPlayer(@NotNull Long id){

        try{
            return Optional.of(repo.findOne(id)).get();
        }catch (NullPointerException e){
            throw new PlayerNotFoundException(String.format("Player with id %s not found", id), e);
        }catch (Exception e){
            throw new PlayerNotFoundException("Error while trying to get Player", e);
        }
    }

    public Iterable<Player> getPlayers(){
        return repo.findAll();
    }

    public Player updatePlayer(@NotNull Player p){

        if(repo.exists(p.getId())){
            try{
                return repo.save(p);
            }catch(DataIntegrityViolationException e){
                throw new SavePlayerException(e);
            }catch(Exception e){
                throw new SavePlayerException("Error Saving Player Data", e);
            }

        }else{
            throw new PlayerNotFoundException("Cannot update a record that doesn't exist");
        }

    }

}
