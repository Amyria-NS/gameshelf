package com.amyria.gameshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amyria.gameshelf.model.GameImage;

public interface GameImageRepository extends JpaRepository<GameImage, Integer>{

}
