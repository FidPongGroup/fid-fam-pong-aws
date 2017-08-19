package com.fidfam.model.domain;

import com.fidfam.model.player.Player;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Josh on 6/19/17.
 */
@Data
@Entity
public class GameTwoPlayer extends Game{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    Player playerOne;

    @ManyToOne
    Player playerTwo;

    int playerOneScore;

    int playerTwoScore;

}
