package com.amyria.gameshelf.specification;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.amyria.gameshelf.model.Game;
import com.amyria.gameshelf.model.enums.Platform;
import com.amyria.gameshelf.model.enums.Status;

@Component
public class GameSpecifications {
	
	public Specification<Game> hasPlatform(Platform platform){
		return (root, query, cb) ->{
			return cb.equal(root.get("platform"), platform);
		};
	}
	
	public Specification<Game> hasStatus(Status status){
		return (root, query, cb) -> cb.equal(root.get("status"), status);
	}
	
	public Specification<Game> titleContains(String search){
		return (root,query,cb) ->{
			return cb.like(cb.lower(root.get("title")), "%" + search.toLowerCase() + "%");
		};
	}
	

}
