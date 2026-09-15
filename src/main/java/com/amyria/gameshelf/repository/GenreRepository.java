package com.amyria.gameshelf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.amyria.gameshelf.model.Genre;

/**
 * Repository interface for accessing {@link Genre} data
 * <br>
 * Extends {@link JpaRepository} to provide standard operations.
 * 
 * @author Amyria
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre,Integer>, JpaSpecificationExecutor<Genre>{
	
	/** Whenever one specific genre is searched, also get the games **/
	@EntityGraph(attributePaths= {"games"})
	Optional<Genre> findById(Integer id);

}
