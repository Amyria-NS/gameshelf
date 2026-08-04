package com.amyria.gameshelf.model;

import java.time.LocalDate;

import com.amyria.gameshelf.model.enums.Platform;
import com.amyria.gameshelf.model.enums.Status;

import jakarta.persistence.*;

@Entity
@Table(name="games")
public class Game {
	
	/** Default Constructor */
	public Game() {
		
	}
	
	/** Unique ID associated with the game */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=100, nullable=false)
	private String title;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Platform platform;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Status status;
	
	private String notes;
	
	@Column(name = "date_added", nullable=false)
	private LocalDate dateAdded;
	
	@Column (name = "date_completed")
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

	public Integer getId() {
		return id;
	}	
	
	

}
