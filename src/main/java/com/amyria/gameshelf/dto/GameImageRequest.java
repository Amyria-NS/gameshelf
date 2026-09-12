package com.amyria.gameshelf.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GameImageRequest {
	
	@NotNull
	private Integer gameId;
	
	//GETTERS AND SETTERS

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	

}
