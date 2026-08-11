package com.amyria.gameshelf.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	
	@GetMapping("/{id}")
	public ResponseEntity<Game> getGame(@PathVariable Integer id){
		Optional<Game> optionalGame = gameService.getGame(id);
		if (optionalGame.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(optionalGame.get());
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Game> updateGame(@PathVariable Integer id, @RequestBody @Valid GameRequest request){
		Optional<Game> optionalGame = gameService.updateGame(id, request);
		if (optionalGame.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(optionalGame.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Game> deleteGame(@PathVariable Integer id){
		Optional<Game> optionalGame = gameService.deleteGame(id);
		if (optionalGame.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(optionalGame.get());
		}
	}

}
