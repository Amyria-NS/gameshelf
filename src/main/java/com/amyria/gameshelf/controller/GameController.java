package com.amyria.gameshelf.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amyria.gameshelf.dto.GameRequest;
import com.amyria.gameshelf.model.Game;
import com.amyria.gameshelf.service.GameService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/games")
@CrossOrigin(origins = "*") 
public class GameController {
	
	private final GameService gameService;
	
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}
	
	@PostMapping
	public ResponseEntity<Game> addGame(@RequestBody @Valid GameRequest request){
		Game savedGame = gameService.createGame(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedGame);
	}

}
