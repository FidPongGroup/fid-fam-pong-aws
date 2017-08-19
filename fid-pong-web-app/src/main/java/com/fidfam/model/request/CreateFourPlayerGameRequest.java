package com.fidfam.model.request;

import lombok.Data;

/**
 * Created by Josh on 7/17/17.
 */
@Data
public class CreateFourPlayerGameRequest extends CreateGameRequest {

    @Data
    public class Team{

        Long playerOneId;
        Long playerTwoId;

    }

    Team teamOne;
    Team teamTwo;
}
