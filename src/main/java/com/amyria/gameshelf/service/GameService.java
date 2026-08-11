package com.amyria.gameshelf.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.amyria.gameshelf.dto.GameRequest;
import com.amyria.gameshelf.model.Game;
import com.amyria.gameshelf.model.enums.Status;
import com.amyria.gameshelf.repository.GameRepository;

@Service
public class GameService {
	
	private final GameRepository gameRepository;
	
	//GameRepository should be provided when GameService is initialized
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
	
	public Game createGame(GameRequest request) {
		
		Game game = new Game();
		
		game.setTitle(request.getTitle());
		game.setPlatform(request.getPlatform());
		game.setStatus(request.getStatus());
		game.setNotes(request.getNotes());
		game.setDateAdded(LocalDate.now());
		
		if (request.getStatus() == Status.COMPLETED) {
			if (request.getDateCompleted() != null) {
				game.setDateCompleted(request.getDateCompleted());
			}
			else {
				game.setDateCompleted(LocalDate.now());
			}
		}
		
		return gameRepository.save(game);
		
	}
	
	public Optional<Game> getGame(int id) {
		return gameRepository.findById(id);	
	}
	
	
	public Optional<Game> updateGame(int id, GameRequest request){
		Optional<Game> optionalGame = gameRepository.findById(id);
		if (optionalGame.isEmpty()) {
			return optionalGame;
		}
		Game game = optionalGame.get();
		
		if (request.getStatus() == Status.COMPLETED) {
			if(request.getDateCompleted() == null && game.getDateCompleted() == null) {
				game.setDateCompleted(LocalDate.now());
			}
			else if(request.getDateCompleted() != null) {
				game.setDateCompleted(request.getDateCompleted());
			}
		}
		
		game.setNotes(request.getNotes());
		game.setPlatform(request.getPlatform());
		game.setStatus(request.getStatus());
		game.setTitle(request.getTitle());
		gameRepository.save(game);
		return optionalGame;
	}
	
	public Optional<Game> deleteGame(int id){
		Optional<Game> optionalGame = gameRepository.findById(id);
		if (optionalGame.isEmpty()) {
			return optionalGame;
		}
		gameRepository.deleteById(id);
		return optionalGame;
	}

}
