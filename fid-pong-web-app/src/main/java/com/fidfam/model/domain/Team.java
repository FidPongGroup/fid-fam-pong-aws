package com.fidfam.model.domain;

import com.fidfam.model.player.Player;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Created by Josh on 7/16/17.
 */
@Data
@Embeddable
public class Team {

    @ManyToOne
    Player playerOne;

    @ManyToOne
    Player playerTwo;
}
