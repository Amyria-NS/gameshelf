package com.amyria.gameshelf.dto;

import java.util.HashSet;
import java.util.Set;

import com.amyria.gameshelf.model.Game;
import com.amyria.gameshelf.model.Genre;

/** 
 * Detailed Game response DTO. This response includes a set of GenreResponses which represent the genres that are associated with this game.
 * @author Amyria-NS
 **/

public class GameResponseDetailed extends GameResponse{
	
	private Set<GenreResponse> genres = new HashSet<>();;

	public GameResponseDetailed(Game game) {
		super(game);
		for (Genre g : game.getGenres()) {
			genres.add(new GenreResponse(g));
		}
		
	}

	public Set<GenreResponse> getGenres() {
		return genres;
	}

	public void setGenres(Set<GenreResponse> genres) {
		this.genres = genres;
	}
	
}
