package com.fidfam.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Josh on 6/19/17.
 */
@Data
@Entity
public class GameTwoPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    Player playerOne;

    @ManyToOne
    Player playerTwo;

    Date startTime;

    String location;

    int playerOneScore;

    int playerTwoScore;

    int gamePoint;
}
