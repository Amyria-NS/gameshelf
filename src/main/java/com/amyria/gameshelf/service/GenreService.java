package com.amyria.gameshelf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.amyria.gameshelf.dto.GenreRequest;
import com.amyria.gameshelf.model.Genre;
import com.amyria.gameshelf.repository.GenreRepository;
import com.amyria.gameshelf.specification.GenreSpecification;

@Service
public class GenreService {
	
	private final GenreRepository genreRepository;
	private final GenreSpecification genreSpecification;
	
	public GenreService(GenreRepository genreRepository, GenreSpecification genreSpecification) {
		this.genreRepository = genreRepository;
		this.genreSpecification = genreSpecification;
	}
	
	public Genre createGenre(GenreRequest request) {
		Genre genre = new Genre();
		genre.setName(request.getName());
		genre.setDescription(request.getDescription());
		return genreRepository.save(genre);
	}
	
	public Optional<Genre> getGenre(int id) {
		return genreRepository.findById(id);
	}
	
	public Optional<Genre> updateGenre(int id, GenreRequest request){
		Optional<Genre> optionalGenre = genreRepository.findById(id);
		if (optionalGenre.isEmpty()) {
			return optionalGenre;
		}
		Genre genre = optionalGenre.get();
		genre.setDescription(request.getDescription());
		genre.setName(request.getName());
		genreRepository.save(genre);
		return optionalGenre;
	}
	
	public Optional<Genre> deleteGenre(int id){
		Optional<Genre> optionalGenre = genreRepository.findById(id);
		genreRepository.deleteById(id);
		return optionalGenre;
	}
	
	public List<Genre> getGenres(Sort.Direction direction, String search){
		Specification<Genre> spec = Specification.unrestricted();
		if (search != null) {
			spec = spec.and(genreSpecification.titleContains(search));
		}
		Sort sort = Sort.by(direction, "name");
		return genreRepository.findAll(spec, sort);
	}

}
