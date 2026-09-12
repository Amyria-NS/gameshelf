package com.amyria.gameshelf.dto;

import com.amyria.gameshelf.model.GameImage;

public class GameImageResponse {
	
	private Integer id;
	private GameResponse game;
	private String imagePath;
	
	
	public GameImageResponse(GameImage gameImage) {
		this.id = gameImage.getId();
		this.game = new GameResponse(gameImage.getGame());
		this.imagePath = gameImage.getImage_path();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public GameResponse getGame() {
		return game;
	}


	public void setGame(GameResponse game) {
		this.game = game;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	

}
