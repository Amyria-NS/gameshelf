package com.amyria.gameshelf.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amyria.gameshelf.dto.GenreRequest;
import com.amyria.gameshelf.model.Genre;
import com.amyria.gameshelf.service.GenreService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/genres")
@CrossOrigin(origins = "*") 
public class GenreController {
	
	private final GenreService genreService;
	
	public GenreController(GenreService genreService) {
		this.genreService = genreService;
	}
	
	@PostMapping
	public ResponseEntity<Genre> createGenre(@RequestBody @Valid GenreRequest request){
		Genre savedGenre = genreService.createGenre(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Genre> getGenreById(@PathVariable Integer id){
		Optional<Genre> optionalGenre = genreService.getGenre(id);
		if (optionalGenre.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(optionalGenre.get());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Genre> updateGenre(@PathVariable Integer id, @RequestBody @Valid GenreRequest request){
		Optional<Genre> optionalGenre = genreService.updateGenre(id, request);
		if (optionalGenre.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(optionalGenre.get());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Genre> deleteGenre(@PathVariable Integer id){
		Optional<Genre> optionalGenre = genreService.deleteGenre(id);
		if (optionalGenre.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(optionalGenre.get());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Genre>> getGenres(
			@RequestParam(defaultValue="ASC") Sort.Direction direction,
			@RequestParam(required=false) String search){
		List<Genre> genres = genreService.getGenres(direction, search);
		return ResponseEntity.ok(genres);
	}

}
