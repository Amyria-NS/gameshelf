package com.amyria.gameshelf.service;

import java.time.LocalDate;

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
	

}
