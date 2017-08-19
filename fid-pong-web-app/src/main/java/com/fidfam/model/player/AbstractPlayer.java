package com.fidfam.model.player;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class AbstractPlayer {
    String firstName;

    String lastName;

    String email;

    String nickname;
}
