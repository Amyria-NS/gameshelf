package com.amyria.gameshelf.model;

import jakarta.persistence.*;

@Entity
@Table(name="games")
public class Game {
	
	/** Default Constructor */
	public Game() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

}
