package com.amyria.gameshelf.dto;

import java.time.LocalDate;
import com.amyria.gameshelf.model.Game;
import com.amyria.gameshelf.model.enums.Platform;
import com.amyria.gameshelf.model.enums.Status;



public class GameResponse {
	
	private Integer id;
	private String title;
	private Platform platform;
	private Status status;
	private String notes;
	private LocalDate dateAdded;
	private LocalDate dateCompleted;
	
	public GameResponse (Game game) {
		this.id = game.getId();
		this.title = game.getTitle();
		this.platform = game.getPlatform();
		this.status = game.getStatus();
		this.notes = game.getNotes();
		this.dateAdded = game.getDateAdded();
		this.dateCompleted = game.getDateCompleted();
	}
	
	/** GETTERS AND SETTERS **/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public LocalDate getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}

	public LocalDate getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(LocalDate dateCompleted) {
		this.dateCompleted = dateCompleted;
	}
	
	
}
