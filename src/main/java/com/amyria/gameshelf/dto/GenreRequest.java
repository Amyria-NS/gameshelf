package com.amyria.gameshelf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating or updating a genre.
 * <br>
 * This class represents the expected structure of the request body sent from the frontend
 * when creating a genre or editing an exiting one.
 * 
 * @author Amyria
 */

public class GenreRequest {
	
	/** REQUIRED FIELDS */
	
	@NotBlank
	@Size(max=100)
	private String name;
	
	/**OPTIONAL FIELDS */
	
	private String description;
	
	/** GETTERS AND SETTERS */

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
