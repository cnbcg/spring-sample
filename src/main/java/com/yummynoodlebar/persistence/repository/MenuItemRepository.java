package com.yummynoodlebar.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.yummynoodlebar.persistence.domain.MenuItem;

public interface MenuItemRepository extends CrudRepository<MenuItem, UUID>, AnalyseIngredients {

	public List<MenuItem> findByIngredientsNameIn(String... name);

}
