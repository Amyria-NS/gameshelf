package com.amyria.gameshelf.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.amyria.gameshelf.dto.GameRequest;
import com.amyria.gameshelf.dto.GameResponse;
import com.amyria.gameshelf.dto.GameResponseDetailed;
import com.amyria.gameshelf.exception.InvalidGenreException;
import com.amyria.gameshelf.model.Game;
import com.amyria.gameshelf.model.Genre;
import com.amyria.gameshelf.model.enums.Platform;
import com.amyria.gameshelf.model.enums.Status;
import com.amyria.gameshelf.repository.GameRepository;
import com.amyria.gameshelf.repository.GenreRepository;
import com.amyria.gameshelf.specification.GameSpecifications;

@Service
public class GameService {
	
	private final GameRepository gameRepository;
	private final GameSpecifications gameSpecifications;
	private final GenreRepository genreRepository;
	
	//GameRepository should be provided when GameService is initialized
	public GameService(GameRepository gameRepository, GenreRepository genreRepository, GameSpecifications gameSpecifications) {
		this.gameRepository = gameRepository;
		this.genreRepository = genreRepository;
		this.gameSpecifications = gameSpecifications;
	}
	
	public GameResponseDetailed createGame(GameRequest request) {
		
		Game game = new Game();
		game.setTitle(request.getTitle());
		game.setPlatform(request.getPlatform());
		game.setStatus(request.getStatus());
		game.setNotes(request.getNotes());
		game.setDateAdded(LocalDate.now());
		
		//If status is complete but no completion date is provided, default to now
		if (request.getStatus() == Status.COMPLETED) {
			if (request.getDateCompleted() != null) {
				game.setDateCompleted(request.getDateCompleted());
			}
			else {
				game.setDateCompleted(LocalDate.now());
			}
		}
		
		//Check genres
		if(request.getGenreIds() != null) {
			Optional<Genre> genre;
			Set<Genre> genreSet = new HashSet<>();
			for (Integer x : request.getGenreIds()) {
				genre = genreRepository.findById(x);
				if (genre.isEmpty()) {
					throw new InvalidGenreException("Invalid genre ID: " + x);
				}
				genreSet.add(genre.get());
			}
			game.setGenres(genreSet);
		}
		
		gameRepository.save(game);
		return new GameResponseDetailed(game);
		
	}
	
	
	
	public Optional<GameResponseDetailed> getGame(int id){
		Optional<GameResponseDetailed> optionalGame = gameRepository.findById(id).map(game -> new GameResponseDetailed(game));
		return optionalGame;
	}
	
	
	public Optional<GameResponseDetailed> updateGame(int id, GameRequest request){
		Optional<Game> optionalGame = gameRepository.findById(id);
		if (optionalGame.isEmpty()) {
			return optionalGame.map(g->new GameResponseDetailed(g));
		}
		Game game = optionalGame.get();
		
		game.setNotes(request.getNotes());
		game.setPlatform(request.getPlatform());
		game.setStatus(request.getStatus());
		game.setTitle(request.getTitle());
		
		//Set dateCompleted to now if status is completed but that field is null
		if (request.getStatus() == Status.COMPLETED) {
			if(request.getDateCompleted() == null && game.getDateCompleted() == null) {
				game.setDateCompleted(LocalDate.now());
			}
			else if(request.getDateCompleted() != null) {
				game.setDateCompleted(request.getDateCompleted());
			}
		}
		
		//Check genres
		if(request.getGenreIds() != null) {
			Optional<Genre> genre;
			Set<Genre> genreSet = new HashSet<>();
			for (Integer x : request.getGenreIds()) {
				genre = genreRepository.findById(x);
				if (genre.isEmpty()) {
					throw new InvalidGenreException("Invalid genre ID: " + x);
				}
				genreSet.add(genre.get());
			}
			game.setGenres(genreSet);
		}
		
		gameRepository.save(game);
		return optionalGame.map(g -> new GameResponseDetailed(g));
	}
	
	public Optional<GameResponseDetailed> deleteGame(int id){
		Optional<GameResponseDetailed> optionalGame = gameRepository.findById(id).map(g->new GameResponseDetailed(g));
		if (optionalGame.isEmpty()) {
			return optionalGame;
		}
		gameRepository.deleteById(id);
		return optionalGame;
	}
	
	public List<GameResponse> getGames(String sortBy, Sort.Direction direction, String search, Status status, Platform platform){
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
		
		return gameRepository.findAll(spec, sort).stream().map(game -> new GameResponse(game)).toList();
	}
		

}
