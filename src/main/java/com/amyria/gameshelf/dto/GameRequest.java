package com.amyria.gameshelf.dto;

import java.time.LocalDate;

import com.amyria.gameshelf.model.enums.Platform;
import com.amyria.gameshelf.model.enums.Status;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for creating or updating a game.
 * <br>
 * This class represents the expected structure of the request body sent from the frontend
 * when creating a game or editing an exiting one.
 * 
 * @author Amyria
 */

public class GameRequest {
	
	/** REQUIRED FIELDS */
	
	@NotBlank
	@Size(max=100)
	private String title;
	
	@NotNull
	private Platform platform;
	
	@NotNull
	private Status status;
	
	/** OPTIONAL FIELDS */
	
	private String notes;
	
	private LocalDate dateCompleted;

	/** GETTERS AND SETTERS */
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDate getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(LocalDate dateCompleted) {
		this.dateCompleted = dateCompleted;
	}
	
	

}
