package com.amyria.gameshelf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.amyria.gameshelf.dto.GenreRequest;
import com.amyria.gameshelf.dto.GenreResponse;
import com.amyria.gameshelf.dto.GenreResponseDetailed;
import com.amyria.gameshelf.model.Genre;
import com.amyria.gameshelf.repository.GenreRepository;
import com.amyria.gameshelf.specification.GenreSpecification;

/**
 * Provides business logic for managing Genres in the GameShelf library.
 * 
 * Handles Genre creation, retrieval, updating, deletion, filtering and sorting while coordinating game and genre persistence.
 * @author Amyria-NS
 */

@Service
public class GenreService {
	
	private final GenreRepository genreRepository;
	private final GenreSpecification genreSpecification;
	
	public GenreService(GenreRepository genreRepository, GenreSpecification genreSpecification) {
		this.genreRepository = genreRepository;
		this.genreSpecification = genreSpecification;
	}
	
	public GenreResponseDetailed createGenre(GenreRequest request) {
		Genre genre = new Genre();
		genre.setName(request.getName());
		genre.setDescription(request.getDescription());
		genreRepository.save(genre);
		return new GenreResponseDetailed(genre);
	}
	
	public Optional<GenreResponseDetailed> getGenre(int id) {
		return genreRepository.findById(id).map(g -> new GenreResponseDetailed(g));
	}
	
	public Optional<GenreResponseDetailed> updateGenre(int id, GenreRequest request){
		Optional<Genre> optionalGenre = genreRepository.findById(id);
		if (optionalGenre.isEmpty()) {
			return optionalGenre.map(g -> new GenreResponseDetailed(g));
		}
		Genre genre = optionalGenre.get();
		genre.setDescription(request.getDescription());
		genre.setName(request.getName());
		genreRepository.save(genre);
		return optionalGenre.map(GenreResponseDetailed::new);
	}
	
	public Optional<GenreResponseDetailed> deleteGenre(int id){
		Optional<Genre> optionalGenre = genreRepository.findById(id);
		genreRepository.deleteById(id);
		return optionalGenre.map(g -> new GenreResponseDetailed(g));
	}
	
	public List<GenreResponse> getGenres(Sort.Direction direction, String search){
		Specification<Genre> spec = Specification.unrestricted();
		if (search != null) {
			spec = spec.and(genreSpecification.titleContains(search));
		}
		Sort sort = Sort.by(direction, "name");
		return genreRepository.findAll(spec, sort).stream().map(g -> new GenreResponse(g)).toList();
	}

}
