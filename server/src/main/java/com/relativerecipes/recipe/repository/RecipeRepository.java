package com.relativerecipes.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.relativerecipes.recipe.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
