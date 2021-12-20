package com.relativerecipes.RelativeRecipes.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.relativerecipes.RelativeRecipes.recipe.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
