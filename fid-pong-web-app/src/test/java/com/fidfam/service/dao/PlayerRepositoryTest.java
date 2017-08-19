package com.fidfam.service.dao;

import com.fidfam.model.player.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.exception.ConstraintViolationException;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Josh on 7/20/17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@TestPropertySource(properties = {"liquibase.contexts=!test"})
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository repo;


    private Player p1;
    private Player p2;

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

    }

    @Test
    public void testSaveAndFindPlayer(){
        Player pClone = new Player();
        BeanUtils.copyProperties(p1, pClone);
        Player savedPlayer = repo.save(p1);

        Long id = savedPlayer.getId();

        Player retrievedPlayer = repo.findOne(id);

        assertThat(retrievedPlayer).isEqualTo(savedPlayer);
        pClone.setId(id);

        assertThat(retrievedPlayer).isEqualTo(pClone);
    }

    @Test
    public void testSaveDuplicatePlayer(){
        p2.setFirstName(p1.getFirstName());
        p2.setLastName(p1.getLastName());
        p2.setEmail(p1.getEmail());
        p2.setNickname(p1.getNickname());

        assertThat(p1).isEqualTo(p2);

        repo.save(p1);
        assertThatThrownBy(() ->repo.save(p2))
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void testDuplicateNickNameFails(){
        repo.save(p1);

        p2.setNickname(p1.getNickname());

        assertThatThrownBy(()->repo.save(p2))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("constraint [\"NICKNAME")
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }
    @Test
    public void testDuplicateEmailFails(){
        repo.save(p1);

        p2.setEmail(p1.getEmail());

        assertThatThrownBy(()->repo.save(p2))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("constraint [\"EMAIL")
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void testNullFirstNameFails(){
        p1.setFirstName(null);

        assertThatThrownBy(()->repo.save(p1))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("constraint [null]")
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void testNullLasttNameFails(){
        p1.setLastName(null);

        assertThatThrownBy(()->{repo.save(p1);})
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("constraint [null]")
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void testNullEmailFails(){
        p1.setEmail(null);

        assertThatThrownBy(()->{repo.save(p1);})
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("constraint [null]")
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }
    @Test
    public void testNullNickName(){
        p1.setNickname(null);

        Player savedPlayer = repo.save(p1);

        assertThat(savedPlayer.getNickname()).isNull();

    }

    @Test
    public void testNullNickNameMultipleSaves(){
        p1.setNickname(null);
        p2.setNickname(null);

        Player savedPlayer1 = repo.save(p1);
        Player savedPlayer2 = repo.save(p2);

        assertThat(savedPlayer1.getNickname()).isNull();
        assertThat(savedPlayer2.getNickname()).isNull();

    }

}