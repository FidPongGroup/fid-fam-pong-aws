package com.fidfam.model.player;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Josh on 6/19/17.
 */
@Data
@Entity
public class Player extends AbstractPlayer{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

}
