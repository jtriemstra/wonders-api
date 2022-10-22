package com.jtriemstra.wonders.api.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.model.board.BoardSide;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // this is to support repeated calls to the BoardFactory from the injected Game. could also just create a new Game in every test
@Slf4j
@Import({TestBase.TestStateConfig.class})
public class GameSerializationTests {

	@Autowired
	PlayerFactory playerFactory;
	
	@Autowired @Qualifier("basicGame")
	Game basicGame;
	
	@Autowired @Qualifier("allFieldObjectMapper")
	ObjectMapper objectMapper;
	
	@Test
	@SneakyThrows
	public void can_write_game() {
		
		String s = objectMapper.writeValueAsString(basicGame);
		log.info(s);
	}

	@Test
	@SneakyThrows
	public void can_write_game_with_player() {
		IPlayer p1 = playerFactory.createPlayer("test1");
		basicGame.addPlayer(p1);
		
		String s = objectMapper.writeValueAsString(basicGame);
		log.info(s);
	}
	
	@Test
	@SneakyThrows
	public void can_write_game_in_progress() {
		basicGame.addPlayer(playerFactory.createPlayer("test1"));
		basicGame.addPlayer(playerFactory.createPlayer("test2"));
		basicGame.addPlayer(playerFactory.createPlayer("test3"));
		
		basicGame.startNextPhase(); //coins/resources
		basicGame.startNextPhase(); // age 1
		
		String s = objectMapper.writeValueAsString(basicGame);
		log.info(s);
	}

	@Test
	@SneakyThrows
	public void can_write_game_with_options() {
		basicGame.addPlayer(playerFactory.createPlayer("test1"));
		basicGame.addPlayer(playerFactory.createPlayer("test2"));
		basicGame.addPlayer(playerFactory.createPlayer("test3"));
		
		basicGame.startNextPhase(); //coins/resources
		basicGame.startNextPhase(); // age 1
		
		basicGame.getPlayer("test1").doAction(new OptionsRequest(), basicGame);
		
		String s = objectMapper.writeValueAsString(basicGame);
		log.info(s);
	}
	
	@TestConfiguration
	public static class TestConfig {

		@Autowired
		private GameFactory gameFactory;

		@Bean
		public Game basicGame() {
			return gameFactory.createGame("testgame", 3, false, BoardSide.A_OR_B, false);
		}
	}
}
