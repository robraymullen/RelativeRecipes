package com.relativerecipes.comment.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.relativerecipes.comment.model.Comment;
import com.relativerecipes.comment.repository.CommentRepository;
import com.relativerecipes.recipe.model.Recipe;
import com.relativerecipes.recipe.repository.RecipeRepository;

@CrossOrigin(maxAge = 3600)
@RestController
public class CommentController {
	
	private CommentRepository commentRepo;
	
	private RecipeRepository recipeRepo;
	
	@Autowired
	public CommentController(CommentRepository commentRepo, RecipeRepository recipeRepo) {
		this.commentRepo = commentRepo;
		this.recipeRepo = recipeRepo;
	}
	
	@GetMapping("/comments/recipe/{id}")
	public List<Comment> getCommentByRecipeId(@PathVariable("id") String id) {
		return this.commentRepo.findByRecipeId(id);
	}
	
	@PutMapping("/comments/recipe/{id}")
	public Comment addComment(@PathVariable("id") String id, @Valid @RequestBody Comment comment) {
		Recipe recipe = recipeRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
		recipe.getComments().add(comment);
		comment.setRecipe(recipe);
		comment.setPostedDate(new Date());
		return commentRepo.save(comment);
	}

}
