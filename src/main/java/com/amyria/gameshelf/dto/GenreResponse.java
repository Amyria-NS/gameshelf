package com.amyria.gameshelf.dto;

import com.amyria.gameshelf.model.Genre;

public class GenreResponse {
	
	private Integer id;
	private String name;
	private String description;
	
	public GenreResponse(Genre genre) {
		this.id = genre.getId();
		this.name = genre.getName();
		this.description = genre.getDescription();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
