package com.amyria.gameshelf.model;

import jakarta.persistence.*;

public class Genre {
	
	/** Default Constructor */
	public Genre() {}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 100, nullable = false, unique = true)
	private String name;
	
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

	public Integer getId() {
		return id;
	}
	
	

}
