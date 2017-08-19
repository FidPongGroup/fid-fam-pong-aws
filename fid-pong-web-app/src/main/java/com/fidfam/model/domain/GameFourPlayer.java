package com.fidfam.model.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Josh on 7/16/17.
 */
@Data
@Entity
public class GameFourPlayer extends Game{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    Long id;

    @AssociationOverrides({
            @AssociationOverride(name = "playerOne", joinColumns = @JoinColumn(name="team_one_player_one_id")),
            @AssociationOverride(name = "playerTwo", joinColumns = @JoinColumn(name="team_one_player_two_id"))
    })
    @Embedded
    Team teamOne;

    @AssociationOverrides({
            @AssociationOverride(name = "playerOne", joinColumns = @JoinColumn(name="team_two_player_one_id")),
            @AssociationOverride(name = "playerTwo", joinColumns = @JoinColumn(name="team_two_player_two_id"))
    })
    @Embedded
    Team teamTwo;

    int teamOneScore;

    int teamTwoScore;

}
