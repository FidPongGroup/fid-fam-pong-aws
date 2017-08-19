package com.fidfam.service;


import com.fidfam.exceptions.player.PlayerNotFoundException;
import com.fidfam.exceptions.player.SavePlayerException;
import com.fidfam.model.player.Player;
import com.fidfam.model.player.CreatePlayerRequest;
import com.fidfam.service.dao.PlayerRepository;
import com.fidfam.test.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.fidfam.exceptions.FailureReason.DATA_VIOLATION;
import static com.fidfam.exceptions.FailureReason.EMAIL;
import static com.fidfam.exceptions.FailureReason.NICKNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

/**
 * Created by Josh on 7/20/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PlayerService.class})
@TestPropertySource(properties = {"liquibase.contexts=!test"})
public class PlayerServiceTest {

    @MockBean
    private PlayerRepository repo;

    @Autowired
    private PlayerService service;


    private Player p1;
    private Player p2;
    private Player p3;

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
        p3.setFirstName("John");
        p3.setLastName("Doe");
        p3.setNickname("Tacos");
        p3.setEmail("john.tacos@email.com");

    }

    @Test
    public void testSaveWorks() throws Exception{

        given(repo.save(p3)).will((a)->{
            Player p = (Player)a.getArgument(0);
            p.setId(4L);
            return p;
        });

        Player savedPlayer = service.createPlayer(p1);

        assertThat(savedPlayer.getId()).isNotNull();
        assertThat(savedPlayer.getFirstName()).isEqualTo(p1.getFirstName());
        assertThat(savedPlayer.getLastName()).isEqualTo(p1.getLastName());
        assertThat(savedPlayer.getNickname()).isEqualTo(p1.getNickname());
        assertThat(savedPlayer.getEmail()).isEqualTo(p1.getEmail());
    }

    @Test
    public void testSaveNotUniqueNickName() throws Exception{

        given(repo.save(p3)).willThrow(TestUtils.createNicknameException());

        assertThatThrownBy(()->service.createPlayer(p1))
                .isInstanceOf(SavePlayerException.class)
                .hasCauseInstanceOf(DataIntegrityViolationException.class)
                .satisfies((e)->{
                    assertThat(((SavePlayerException)e).getReason())
                            .isEqualTo(NICKNAME);
                });

    }
    @Test
    public void testSaveNotUniqueEmail() throws Exception{

        given(repo.save(p3)).willThrow(TestUtils.createEmailException());
        try{
            service.createPlayer(p1);
        }catch(SavePlayerException e){
            System.out.println(e);
        }


        assertThatThrownBy(()->service.createPlayer(p1))
                .isInstanceOf(SavePlayerException.class)
                .hasCauseInstanceOf(DataIntegrityViolationException.class)
                .satisfies((e)->{
                    assertThat(((SavePlayerException)e).getReason())
                            .isEqualTo(EMAIL);
                });

    }
    @Test
    public void testSaveGenericBadRequest() throws Exception{

        given(repo.save(p3)).willThrow(TestUtils.createOtherException());

        assertThatThrownBy(()->service.createPlayer(p1))
                .isInstanceOf(SavePlayerException.class)
                .hasCauseInstanceOf(DataIntegrityViolationException.class)
                .satisfies((e)->{
                    assertThat(((SavePlayerException)e).getReason())
                            .isEqualTo(DATA_VIOLATION);
                });

    }

    @Test
    public void testGetPlayerSuccess() throws Exception{
        p3.setId(4L);
        given(repo.findOne(p3.getId())).willReturn(p3);

        assertThat(service.getPlayer(4L)).isEqualTo(p3);


    }
    @Test
    public void testGetPlayerWhenPlayerNotFound() throws Exception{
        p3.setId(4L);
        given(repo.findOne(p3.getId())).willReturn(null);

        assertThatThrownBy(() -> service.getPlayer(4L))
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessage("Player with id 4 not found")
                .hasRootCauseExactlyInstanceOf(NullPointerException.class);

    }
    @Test
    public void testGetPlayerWhenPlayerNotFoundAndUnknownException() throws Exception{
        p3.setId(4L);
        given(repo.findOne(p3.getId())).willThrow(new DataIntegrityViolationException("Can't do that"));
        assertThatThrownBy(() -> service.getPlayer(4L))
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessage("Error while trying to get Player")
                .hasRootCauseExactlyInstanceOf(DataIntegrityViolationException.class);

    }


}