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
import com.amyria.gameshelf.dto.GenreResponse;
import com.amyria.gameshelf.dto.GenreResponseDetailed;
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
	public ResponseEntity<GenreResponseDetailed> createGenre(@RequestBody @Valid GenreRequest request){
		GenreResponseDetailed savedGenre = genreService.createGenre(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GenreResponseDetailed> getGenreById(@PathVariable Integer id){
		Optional<GenreResponseDetailed> optionalGenre = genreService.getGenre(id);
		if (optionalGenre.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(optionalGenre.get());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<GenreResponseDetailed> updateGenre(@PathVariable Integer id, @RequestBody @Valid GenreRequest request){
		Optional<GenreResponseDetailed> optionalGenre = genreService.updateGenre(id, request);
		if (optionalGenre.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(optionalGenre.get());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<GenreResponseDetailed> deleteGenre(@PathVariable Integer id){
		Optional<GenreResponseDetailed> optionalGenre = genreService.deleteGenre(id);
		if (optionalGenre.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(optionalGenre.get());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<GenreResponse>> getGenres(
			@RequestParam(defaultValue="ASC") Sort.Direction direction,
			@RequestParam(required=false) String search){
		List<GenreResponse> genres = genreService.getGenres(direction, search);
		return ResponseEntity.ok(genres);
	}

}
