package com.amyria.gameshelf.repository;

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

}
