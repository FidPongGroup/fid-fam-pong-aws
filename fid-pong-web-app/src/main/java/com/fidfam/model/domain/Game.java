package com.fidfam.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Josh on 7/16/17.
 */
@Data
@MappedSuperclass
abstract public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String location;
    Date startTime;
    int gamePoint;

}
