package com.amyria.gameshelf.testData;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.amyria.gameshelf.dto.GameRequest;
import com.amyria.gameshelf.model.Game;
import com.amyria.gameshelf.model.Genre;
import com.amyria.gameshelf.model.enums.Platform;
import com.amyria.gameshelf.model.enums.Status;

public class TestDataFactory {



    public Game createSimpleGame() {
        Game game = new Game();
        game.setTitle("Test Game");
        game.setPlatform(Platform.PC);
        game.setStatus(Status.BACKLOG);
        game.setNotes("Test notes");
        game.setDateAdded(LocalDate.of(2026, 1, 1));
        game.setGenres(new HashSet<>());
        return game;
    }
    
    public Game createGameWithGenre() {
    	Game game = createSimpleGame();
    	Set<Genre> genres = new HashSet<>();
    	genres.add(createSimpleGenre());
    	return game;
    }
    

    public Genre createSimpleGenre() {
        Genre genre = new Genre();
        genre.setName("Test Genre");
        genre.setDescription("Test genre description");
        return genre;
    }
    
    public GameRequest gameRequestNotCompleted() {
    	GameRequest gameReq = new GameRequest();
    	gameReq.setTitle("Test Game");
    	gameReq.setNotes("Test notes");
    	gameReq.setStatus(Status.BACKLOG);
    	gameReq.setPlatform(Platform.GAMECUBE);
    	return gameReq;
    }
    
    public GameRequest gameRequestCompletedNoCompletedDate() {
    	GameRequest gameReq = new GameRequest();
    	gameReq.setTitle("Test Game");
    	gameReq.setNotes("Test notes");
    	gameReq.setStatus(Status.COMPLETED);
    	gameReq.setPlatform(Platform.GAMECUBE);
    	return gameReq;
    }
    
    public GameRequest gameRequestCompletedWithCompletedDate() {
    	GameRequest gameReq = new GameRequest();
    	gameReq.setTitle("Test Game");
    	gameReq.setNotes("Test notes");
    	gameReq.setStatus(Status.COMPLETED);
    	gameReq.setPlatform(Platform.GAMECUBE);
    	gameReq.setDateCompleted(LocalDate.of(2026, 1, 1));
    	return gameReq;
    }
    
    public GameRequest gameRequestWithGenre() {
    	GameRequest gameReq = new GameRequest();
    	gameReq.setTitle("Test Game");
    	gameReq.setNotes("Test notes");
    	gameReq.setStatus(Status.BACKLOG);
    	gameReq.setPlatform(Platform.GAMECUBE);
    	gameReq.setGenreIds(new HashSet<Integer>(Set.of(1)));
    	return gameReq;
    }
    
    public GameRequest gameRequestEmptyGenres() {
    	GameRequest gameReq = new GameRequest();
    	gameReq.setTitle("Test Game");
    	gameReq.setNotes("Test notes");
    	gameReq.setStatus(Status.BACKLOG);
    	gameReq.setPlatform(Platform.GAMECUBE);
    	gameReq.setGenreIds(new HashSet<>());
    	return gameReq;
    }
    
    


}
