package com.amyria.gameshelf.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

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
import com.amyria.gameshelf.testData.TestDataFactory;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
	
	private TestDataFactory factory = new TestDataFactory();
	
	@Mock
	private GameRepository gameRepository;
	
	@Mock
	private GenreRepository genreRepository;
	
	@Mock
	private GameSpecifications gameSpecifications;
	
	@Mock 
	private Specification<Game> titleSpec, statusSpec, platformSpec;
	
	@InjectMocks
	private GameService gameService;
	
	@Captor
	private ArgumentCaptor<Game> gameCaptor;
	
	@Test
	void getGameById_existingId_returnsGame() {
		int id = 1;
		//arrange
		Game game = factory.createSimpleGame();
		when(gameRepository.findById(id)).thenReturn(Optional.of(game));
		//act
		Optional<GameResponseDetailed> result = gameService.getGame(id);
		//assert
		assertThat(result).isNotEmpty();
		assertThat(result.get().getTitle()).isEqualTo(game.getTitle());
		verify(gameRepository, times(1)).findById(id);
	}
	
	@Test
	void getGame_nonExistentId_returnsEmpty() {
		int id = 2;
		//arrange
		when(gameRepository.findById(id)).thenReturn(Optional.empty());
		//act
		Optional<GameResponseDetailed> result = gameService.getGame(id);
		//assert
		assertThat(result).isEmpty();
		verify(gameRepository, times(1)).findById(id);
	}
	
	@Test
	void createGame_validRequestNotCompleted_returnsCreatedGame() {
		GameRequest request = factory.gameRequestNotCompleted();
		GameResponseDetailed result = gameService.createGame(request);
		assertThat(result.getTitle()).isEqualTo(request.getTitle());
		assertThat(result.getDateCompleted()).isNull();
		verify(gameRepository, times(1)).save(any(Game.class));
	}
	
	@Test
	void createGame_completedWithoutDate_defaultsCompletionDate() {
		GameRequest request = factory.gameRequestCompletedNoCompletedDate();
		GameResponseDetailed result = gameService.createGame(request);
		verify(gameRepository, times(1)).save(gameCaptor.capture());
		Game game = gameCaptor.getValue();
		assertThat(result.getDateCompleted()).isEqualTo(LocalDate.now());
		assertThat(game.getDateCompleted()).isEqualTo(LocalDate.now());
	}
	
	@Test
	void createGame_completedWithDate_keepsCompletionDate() {
		GameRequest request = factory.gameRequestCompletedWithCompletedDate();
		GameResponseDetailed result = gameService.createGame(request);
		verify(gameRepository, times(1)).save(gameCaptor.capture());
		Game game = gameCaptor.getValue();
		assertThat(result.getDateCompleted()).isEqualTo(request.getDateCompleted());
		assertThat(game.getDateCompleted()).isEqualTo(request.getDateCompleted());
	}
	
	@Test
	void createGame_invalidGenreId_throwsException() {
		GameRequest request = factory.gameRequestWithGenre();
		when(genreRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(InvalidGenreException.class, () -> gameService.createGame(request));
		verify(genreRepository, times(1)).findById(1);
	}
	
	@Test
	void createGame_validGenreId_returnsCreatedGame() {
		GameRequest request = factory.gameRequestWithGenre();
		Genre genre = factory.createSimpleGenre();
		when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
		GameResponseDetailed response = gameService.createGame(request);
		assertThat(response.getGenres()).isNotEmpty();
		assertThat(response.getGenres().size()).isEqualTo(1);
		verify(genreRepository, times(1)).findById(1);
	}
	
	@Test
	void updateGame_existingId_updatesGame() {
		//fields in game and request do not match
		GameRequest request = factory.gameRequestNotCompleted();
		Game game = factory.createSimpleGame();
		int id = 3;
		when (gameRepository.findById(3)).thenReturn(Optional.of(game));
		Optional<GameResponseDetailed> response = gameService.updateGame(id, request);
		verify(gameRepository, times(1)).save(gameCaptor.capture());
		verify(gameRepository, times(1)).findById(id);
		Game captured = gameCaptor.getValue();
		assertThat(response).isNotEmpty();
		assertThat(response.get().getNotes()).isEqualTo(request.getNotes());
		assertThat(response.get().getTitle()).isEqualTo(request.getTitle());
		assertThat(response.get().getId()).isEqualTo(captured.getId());	
	}
	
	@Test
	void updateGame_genreIdsNull_preservesGenres() {
		Game game = factory.createGameWithGenre();
		// request has same fields as game except platform is GAME in request and PC in game. No genres in request
		GameRequest request = factory.gameRequestNotCompleted();
		Platform originalPlatform = game.getPlatform();
		Set<Genre> originalGenres = new HashSet<>(game.getGenres());
		int id = 4;
		when(gameRepository.findById(id)).thenReturn(Optional.of(game));
		Optional<GameResponseDetailed> response = gameService.updateGame(id, request);
		verify(gameRepository, times(1)).save(gameCaptor.capture());
		verify(gameRepository, times(1)).findById(id);
		Game captured = gameCaptor.getValue();
		assertThat(response).isNotEmpty();
		assertThat(originalPlatform).isNotEqualTo(captured.getPlatform());
		assertThat(response.get().getGenres().size()).isEqualTo(originalGenres.size());
		assertThat(originalGenres).isEqualTo(captured.getGenres());
	}
		
	
	@Test
	void updateGame_emptyGenreIds_clearsGenres() {
		Game game = factory.createGameWithGenre();
		GameRequest request = factory.gameRequestEmptyGenres();
		int id = 5;
		when(gameRepository.findById(id)).thenReturn(Optional.of(game));
		Optional<GameResponseDetailed> response = gameService.updateGame(id, request);
		verify(gameRepository, times(1)).save(gameCaptor.capture());
		verify(gameRepository, times(1)).findById(id);
		Game captured = gameCaptor.getValue();
		assertThat(response).isNotEmpty();
		assertThat(response.get().getGenres().size()).isEqualTo(0);
		assertThat(captured.getGenres().size()).isEqualTo(0);
	}
	
	@Test
	void updateGame_invalidGenreId_throwsException(){
		Game game = factory.createSimpleGame();
		GameRequest request = factory.gameRequestWithGenre();
		int id = 5;
		when(genreRepository.findById(1)).thenReturn(Optional.empty());
		when(gameRepository.findById(id)).thenReturn(Optional.of(game));
		assertThrows(InvalidGenreException.class, () -> gameService.updateGame(id, request));
		verify(genreRepository, times(1)).findById(1);
	}
	
	@Test
	void updateGame_validGenreId_keepsGenre() {
		Game game = factory.createGameWithGenre();
		Genre genre = factory.createSimpleGenre();
		GameRequest request = factory.gameRequestWithGenre();
		int gameId = 6;
		when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
		when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
		Optional<GameResponseDetailed> response = gameService.updateGame(gameId, request);
		assertThat(response.isPresent());
		assertThat(response.get().getGenres().size()).isEqualTo(1);
		verify(genreRepository, times(1)).findById(1);
		verify(gameRepository, times(1)).findById(gameId);
	}
	
	@Test
	void deleteGame_validGameId_callsDelete() {
		Game game = factory.createSimpleGame();
		int id = 6;
		when(gameRepository.findById(id)).thenReturn(Optional.of(game));
		Optional<GameResponseDetailed> response = gameService.deleteGame(id);
		assertThat(response.get().getId()).isEqualTo(game.getId());
		verify(gameRepository, times(1)).deleteById(id);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	void getGames_noSpecifications_callsFindAll() {
		List<Game> games = List.of(factory.createGameWithGenre(), factory.createSimpleGame());
		when(gameRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(games);
		List<GameResponse> response = gameService.getGames("title", Direction.ASC, null, null, null);
		assertThat(response.size()).isEqualTo(2);
		verify(gameRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void getGames_allSpecifications_callsGameSpecifications() {
		List<Game> games = List.of(factory.createGameWithGenre(), factory.createSimpleGame());
		when(gameRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(games);
		//Prepare specifications
		Platform platform = Platform.GAMECUBE;
		Status status = Status.BACKLOG;
		String search = "test";
		when(gameSpecifications.hasPlatform(platform)).thenReturn(platformSpec);
		when(gameSpecifications.hasStatus(status)).thenReturn(statusSpec);
		when(gameSpecifications.titleContains(search)).thenReturn(titleSpec);
		List<GameResponse> response = gameService.getGames("title", Direction.DESC, search, status, platform);
		assertThat(response.size()).isEqualTo(2);
		verify(gameRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
		verify(gameSpecifications, times(1)).hasPlatform(platform);
		verify(gameSpecifications, times(1)).hasStatus(status);
		verify(gameSpecifications, times(1)).titleContains(search);
	}
	

}


/** 
 * POTENTIALLY TO ADD:
 * updateGame_completedWithExistingDate_preservesDate()
 * updateGame_completedWithNewDate_replacesDate()
 * deleteGame_nonExistentId_doesNotDelete()
 **/