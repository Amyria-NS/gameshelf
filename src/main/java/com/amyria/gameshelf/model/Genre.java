package com.amyria.gameshelf.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name="genres")
public class Genre {
	
	public Genre() {}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 100, nullable = false, unique = true)
	private String name;
	
	private String description;
	
	/** Set of Game objects that are associated with this Genre **/
	@ManyToMany(mappedBy="genres")
	private Set<Game> games = new HashSet<Game>();

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

	public Integer getId() {
		return id;
	}
	
	public void setGames(Set<Game> games){
		this.games = games;
	}
	
	public Set<Game> getGames(){
		return games;
	}
	
	

}
