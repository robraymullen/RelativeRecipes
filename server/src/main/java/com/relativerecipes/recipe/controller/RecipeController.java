package com.relativerecipes.recipe.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.relativerecipes.recipe.model.Recipe;
import com.relativerecipes.recipe.repository.RecipeRepository;

@CrossOrigin(maxAge = 3600)
@RestController
public class RecipeController {
	
	private RecipeRepository recipeRepo;
	
	@Autowired
	public RecipeController(RecipeRepository recipeRepo) {
		this.recipeRepo = recipeRepo;
	}
	
	@GetMapping("/recipes")
	public List<Recipe> getAllRecipes() {
		return recipeRepo.findAll();
	}
	
	@GetMapping("/recipes/{id}")
	public Recipe getRecipeById(@PathVariable("id") Long id) {
		return recipeRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
	}
	
	@PutMapping("/recipes")
	public Recipe addRecipe(@Valid @RequestBody Recipe newRecipe) {
		newRecipe.setPostedDate(new Date());
		return recipeRepo.save(newRecipe);
	}
	
	@DeleteMapping("/recipes/{id}")
	public void deleteRecipe(@PathVariable("id") Long id) {
		recipeRepo.deleteById(id);
	}

}
