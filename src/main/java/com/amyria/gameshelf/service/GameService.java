package com.amyria.gameshelf.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.amyria.gameshelf.dto.GameRequest;
import com.amyria.gameshelf.model.Game;
import com.amyria.gameshelf.model.enums.Platform;
import com.amyria.gameshelf.model.enums.Status;
import com.amyria.gameshelf.repository.GameRepository;
import com.amyria.gameshelf.specification.GameSpecifications;

@Service
public class GameService {
	
	private final GameRepository gameRepository;
	private final GameSpecifications gameSpecifications;
	
	//GameRepository should be provided when GameService is initialized
	public GameService(GameRepository gameRepository, GameSpecifications gameSpecifications) {
		this.gameRepository = gameRepository;
		this.gameSpecifications = gameSpecifications;
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
	
	public List<Game> getGames(String sortBy, Sort.Direction direction, String search, Status status, Platform platform){
		Specification<Game> spec = Specification.unrestricted();
		if (search != null) {
			spec = spec.and(gameSpecifications.titleContains(search));
		}
		if (status != null) {
			spec = spec.and(gameSpecifications.hasStatus(status));
		}
		if (platform != null) {
			spec=spec.and(gameSpecifications.hasPlatform(platform));
		}
		Sort sort = Sort.by(direction, sortBy);
		
		return gameRepository.findAll(spec, sort);
	}
	

}
