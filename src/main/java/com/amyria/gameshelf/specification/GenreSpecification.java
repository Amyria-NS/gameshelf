package com.amyria.gameshelf.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.amyria.gameshelf.model.Genre;

@Component
public class GenreSpecification {
	
	public Specification<Genre> titleContains(String search){
		return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%");
	}
	

}
