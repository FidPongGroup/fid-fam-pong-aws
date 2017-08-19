package com.fidfam.service.dao;

import com.fidfam.model.domain.GameTwoPlayer;
import com.fidfam.model.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbc.JdbcSQLException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by Josh on 7/20/17.
 */
@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@TestPropertySource(properties = {"liquibase.contexts=!test"})
public class TwoPlayerGameRepositoryTest {

    @Autowired
    private TwoPlayerGameRepository gameRepo;

    @Autowired
    private PlayerRepository playerRepo;

    private GameTwoPlayer g1;

    private Player p1;
    private Player p2;
    private Player p3;

    private Date startDate = new GregorianCalendar(2017,
            Calendar.FEBRUARY,
            11,
            9,
            3,
            0).getTime();

    @Before
    public void setUp(){
        this.p1 = new Player();
        p1.setFirstName("John");
        p1.setLastName("Doe");
        p1.setNickname("Tacos");
        p1.setEmail("john.tacos@email.com");

        this.p2 = new Player();
        p2.setFirstName("Jane");
        p2.setLastName("Doe");
        p2.setNickname("Rule");
        p2.setEmail("jane.rule@email.com");

        this.p3 = new Player();
        p3.setFirstName("Jack");
        p3.setLastName("Sparrow");
        p3.setNickname("Captain");
        p3.setEmail("black.pearl@email.com");

        playerRepo.save(p1);
        playerRepo.save(p2);

        this.g1 = new GameTwoPlayer();
        g1.setPlayerOne(p1);
        g1.setPlayerTwo(p2);
        g1.setPlayerOneScore(3);
        g1.setPlayerTwoScore(4);
        g1.setLocation("3rd Floor");
        g1.setStartTime(startDate);

    }

    @Test
    public void testSaveFunctionality(){
        GameTwoPlayer savedGame = gameRepo.save(g1);

        assertThat(savedGame.getId()).isNotNull();

        GameTwoPlayer retrievedGame = gameRepo.findOne(savedGame.getId());

        assertThat(savedGame).isEqualTo(retrievedGame);
    }

    @Test
    public void testFailwithNullPlayerOneId(){

        g1.setPlayerOne(p3);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement; SQL [n/a]; constraint [null]")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }

    @Test
    public void testFailwithNullPlayerTwoId(){

        g1.setPlayerTwo(p3);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement; SQL [n/a]; constraint [null]")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }
    @Test
    public void testFailwithNotPresentPlayerOneId(){
        p3.setId(700L);
        g1.setPlayerOne(p3);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("FOREIGN KEY(PLAYER_ONE_ID)")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }
    @Test
    public void testFailwithNotPresentPlayerTwoId(){

        p3.setId(700L);
        g1.setPlayerTwo(p3);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("FOREIGN KEY(PLAYER_TWO_ID)")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }
    @Test
    public void testFailWithNullDate(){

        g1.setStartTime(null);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("constraint [null]")
                .hasCauseInstanceOf(ConstraintViolationException.class)
                .hasRootCauseExactlyInstanceOf(JdbcSQLException.class);
    }

    @Test
    public void testSaveWithNullLocation(){

        g1.setLocation(null);

        assertThat(gameRepo.save(g1).getLocation()).isNull();
    }

}