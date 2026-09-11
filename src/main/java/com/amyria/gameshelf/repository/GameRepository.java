package com.amyria.gameshelf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.amyria.gameshelf.model.Game;

/**
 * Repository interface for accessing {@link Game} data
 * <br>
 * Extends {@link JpaRepository} to provide standard operations.
 * 
 * @author Amyria
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Integer>, JpaSpecificationExecutor<Game>{
	
	/** Whenever one specific game is searched, also get the genres **/
	@EntityGraph(attributePaths= {"genres"})
	Optional<Game> findById(int id);
	
	

}
