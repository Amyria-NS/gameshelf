package com.amyria.gameshelf.dto;

import java.util.HashSet;
import java.util.Set;

import com.amyria.gameshelf.model.Game;
import com.amyria.gameshelf.model.Genre;

public class GenreResponseDetailed extends GenreResponse {

	private Set<GameResponse> games = new HashSet<>();
	
	public GenreResponseDetailed(Genre genre) {
		super(genre);
		for (Game g : genre.getGames()) {
			games.add(new GameResponse(g));
		}
	}

	public Set<GameResponse> getGames() {
		return games;
	}

	public void setGames(Set<GameResponse> games) {
		this.games = games;
	}

}
