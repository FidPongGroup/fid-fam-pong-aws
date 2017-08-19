package com.fidfam.service.dao;

import com.fidfam.model.domain.GameFourPlayer;

import com.fidfam.model.player.Player;
import com.fidfam.model.domain.Team;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbc.JdbcSQLException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
public class FourPlayerGameRepositoryTest {

    @Autowired
    private FourPlayerGameRepository gameRepo;

    @Autowired
    private PlayerRepository playerRepo;

    private GameFourPlayer g1;

    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    private Player p5;
    
    private Team t1;
    
    private Team t2;

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

        this.p4 = new Player();
        p4.setFirstName("Daenerys");
        p4.setLastName("Targaryen");
        p4.setNickname("Mother of Dragons");
        p4.setEmail("fire.and.blook@email.com");

        this.p5 = new Player();
        p5.setFirstName("Ned");
        p5.setLastName("Stark");
        p5.setNickname("Headless Ned");
        p5.setEmail("winter.is.coming@email.com");

        playerRepo.save(p1);
        playerRepo.save(p2);
        playerRepo.save(p3);
        playerRepo.save(p4);

        this.t1 = new Team();
        t1.setPlayerOne(p1);
        t1.setPlayerTwo(p2);
        
        this.t2 = new Team();
        t2.setPlayerOne(p3);
        t2.setPlayerTwo(p4);

        this.g1 = new GameFourPlayer();
        g1.setTeamOne(t1);
        g1.setTeamTwo(t2);
        g1.setTeamOneScore(3);
        g1.setTeamTwoScore(4);
        g1.setLocation("3rd Floor");
        g1.setStartTime(startDate);

    }

    @Test
    public void testSaveFunctionality(){
        GameFourPlayer savedGame = gameRepo.save(g1);

        assertThat(savedGame.getId()).isNotNull();

        GameFourPlayer retrievedGame = gameRepo.findOne(savedGame.getId());

        assertThat(savedGame).isEqualTo(retrievedGame);
    }

    @Test
    public void testFailWithNullTeamOnePlayerOneId(){

        g1.getTeamOne().setPlayerOne(p5);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement; SQL [n/a]; constraint [null]")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }

    @Test
    public void testFailWithNullTeamOnePlayerTwoId(){

        g1.getTeamOne().setPlayerTwo(p5);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement; SQL [n/a]; constraint [null]")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }
    @Test
    public void testFailWithNullTeamTwoPlayerOneId(){

        g1.getTeamTwo().setPlayerOne(p5);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement; SQL [n/a]; constraint [null]")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }
    @Test
    public void testFailWithNullTeamTwoPlayerTwoId(){

        g1.getTeamTwo().setPlayerTwo(p5);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement; SQL [n/a]; constraint [null]")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }
    @Test
    public void testFailWithNotPresentTeamOnePlayerOneId(){
        p5.setId(700L);
        g1.getTeamOne().setPlayerOne(p5);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("TEAM_ONE_PLAYER_ONE_ID_FK")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }
    @Test
    public void testFailwithNotPresentTeamOnePlayerTwoId(){

        p5.setId(700L);
        g1.getTeamOne().setPlayerTwo(p5);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("TEAM_ONE_PLAYER_TWO_ID_FK")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }
    @Test
    public void testFailWithNotPresentTeamTwoPlayerOneId(){
        p5.setId(700L);
        g1.getTeamTwo().setPlayerOne(p5);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("TEAM_TWO_PLAYER_ONE_ID_FK")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }
    @Test
    public void testFailWithNotPresentTeamTwoPlayerTwoId(){

        p5.setId(700L);
        g1.getTeamTwo().setPlayerTwo(p5);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("TEAM_TWO_PLAYER_TWO_ID_FK")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }

    @Test
    public void testFailWithNotPresentTeamOne(){

        g1.setTeamOne(null);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("constraint [null]")
                .hasCauseInstanceOf(ConstraintViolationException.class);

    }
    @Test
    public void testFailWithNotPresentTeamTwo(){

        g1.setTeamTwo(null);

        assertThatThrownBy(() -> gameRepo.save(g1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("constraint [null]")
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