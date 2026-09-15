package com.amyria.gameshelf.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.amyria.gameshelf.model.enums.Platform;
import com.amyria.gameshelf.model.enums.Status;

import jakarta.persistence.*;

@Entity
@Table(name="games")
public class Game {
	
	public Game() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=100, nullable=false)
	private String title;
	
	@Enumerated(EnumType.STRING)
	@JdbcTypeCode(SqlTypes.NAMED_ENUM)
	@Column(nullable=false)
	private Platform platform;
	
	@Enumerated(EnumType.STRING)
	@JdbcTypeCode(SqlTypes.NAMED_ENUM)
	@Column(nullable=false)
	private Status status;
	
	private String notes;
	
	@Column(name = "date_added", nullable=false)
	private LocalDate dateAdded;
	
	@Column (name = "date_completed")
	private LocalDate dateCompleted;
	
	/** List of Genre objects associated with this game **/
	@ManyToMany
	@JoinTable(name="game_genres", joinColumns = @JoinColumn(name="game_id"), inverseJoinColumns = @JoinColumn(name="genre_id"))
	private Set<Genre> genres = new HashSet<Genre>();
	
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
	
	public Set<Genre> getGenres(){
		return genres;
	}
	
	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}
	

}
