package com.amyria.gameshelf.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.amyria.gameshelf.dto.GenreRequest;
import com.amyria.gameshelf.dto.GenreResponse;
import com.amyria.gameshelf.dto.GenreResponseDetailed;
import com.amyria.gameshelf.model.Genre;
import com.amyria.gameshelf.repository.GameRepository;
import com.amyria.gameshelf.repository.GenreRepository;
import com.amyria.gameshelf.specification.GenreSpecification;
import com.amyria.gameshelf.testData.TestDataFactory;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {
	
	@Mock
	private GenreRepository genreRepository;
	
	@Mock
	private GameRepository gameRepository;
	
	@Mock
	private GenreSpecification genreSpecification;
	
	@Mock
	private Specification<Genre> titleSpec;
	
	@InjectMocks
	private GenreService genreService;
	
	@Captor
	private ArgumentCaptor<Genre> genreCaptor;
	
	private TestDataFactory factory = new TestDataFactory();
	
	@Test
	void getGenreById_existingId_returnsGenre() {
		Genre genre = factory.createSimpleGenre();
		String originalName = genre.getName();
		int id = 1;
		when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
		Optional<GenreResponseDetailed> response = genreService.getGenre(id);
		assertThat(response).isNotEmpty();
		assertThat(response.get().getName()).isEqualTo(originalName);
	}
	
	@Test
	void getGenreById_nonExistingId_returnsEmpty() {
		int id = 2;
		when(genreRepository.findById(id)).thenReturn(Optional.empty());
		Optional<GenreResponseDetailed> response = genreService.getGenre(id);
		assertThat(response).isEmpty();
	}
	
	@Test
	void createGenre_validRequest_returnsCreatedGenre() {
		GenreRequest request = factory.genreRequestSimple();
		GenreResponseDetailed response = genreService.createGenre(request);
		verify(genreRepository, times(1)).save(genreCaptor.capture());
		assertThat(response.getName()).isEqualTo(request.getName());
		assertThat(response.getDescription()).isEqualTo(request.getDescription());
		assertThat(genreCaptor.getValue().getName()).isEqualTo(request.getName());
		assertThat(genreCaptor.getValue().getDescription()).isEqualTo(request.getDescription());
	}
	
	@Test
	void deleteGenre_validId_returnsDeletedGenre() {
		int id = 5;
		Genre genre = factory.createSimpleGenre();
		when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
		Optional<GenreResponseDetailed> response = genreService.deleteGenre(id);
		assertThat(response.isPresent());
		assertThat(response.get().getName()).isEqualTo(genre.getName());
		verify(genreRepository, times(1)).findById(id);
	}
	
	@Test
	void deleteGenre_inValidId_returnsEmptyOptional() {
		int id = 7;
		when(genreRepository.findById(id)).thenReturn(Optional.empty());
		Optional<GenreResponseDetailed> response = genreService.deleteGenre(id);
		assertThat(response.isEmpty());
		verify(genreRepository, times(1)).findById(id);
	}
	
	@Test
	void updateGenre_validId_returnsUpdatedGenre() {
		int id = 8;
		Genre genre = factory.createSimpleGenre2();
		String genreOriginalName = genre.getName();
		when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
		GenreRequest request = factory.genreRequestSimple();
		Optional<GenreResponseDetailed> response = genreService.updateGenre(id, request);
		assertThat(response.isPresent());
		assertThat(response.get().getName()).isEqualTo(request.getName());
		assertThat(response.get().getName()).isNotEqualTo(genreOriginalName);
		verify(genreRepository, times(1)).findById(id);
	}
	
	@Test
	void updateGenre_invalidId_returnsEmptyOptional() {
		int id = 9;
		when(genreRepository.findById(id)).thenReturn(Optional.empty());
		GenreRequest request = factory.genreRequestSimple();
		Optional<GenreResponseDetailed> response = genreService.updateGenre(id, request);
		assertThat(response.isEmpty());
		verify(genreRepository, times(1)).findById(id);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void getGenres_noSpecification_returnsGenres() {
		List<Genre> genres = List.of(factory.createSimpleGenre(), factory.createSimpleGenre2());
		when(genreRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(genres);
		List<GenreResponse> response = genreService.getGenres(Direction.ASC, null);
		assertThat(response.size()).isEqualTo(genres.size());
		verify(genreRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void getGenres_withSpecification_returnsGenres() {
		List<Genre> genres = List.of(factory.createSimpleGenre(), factory.createSimpleGenre2());
		when(genreRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(genres);
		String search = "zelda";
		when(genreSpecification.titleContains(search)).thenReturn(titleSpec);
		List<GenreResponse> response = genreService.getGenres(Direction.ASC, search);
		assertThat(response.size()).isEqualTo(genres.size());
		verify(genreRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
		verify(genreSpecification, times(1)).titleContains(search);
	}

}
