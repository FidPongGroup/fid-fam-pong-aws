package com.fidfam.model.request;

import lombok.Data;

/**
 * Created by Josh on 7/17/17.
 */
@Data
public class CreateTwoPlayerGameRequest extends CreateGameRequest {

    Long playerOneId;
    Long playerTwoId;
}
