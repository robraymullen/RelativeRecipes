package com.relativerecipes.comment.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.relativerecipes.comment.model.Comment;
import com.relativerecipes.comment.repository.CommentRepository;
import com.relativerecipes.recipe.model.Recipe;

@CrossOrigin(maxAge = 3600)
@RestController
public class CommentController {
	
	private CommentRepository commentRepo;
	
	@Autowired
	public CommentController(CommentRepository commentRepo) {
		this.commentRepo = commentRepo;
	}
	
	@GetMapping("/comments/recipe/{id}")
	public List<Comment> getCommentByRecipeId(@PathVariable("id") Long id) {
		return this.commentRepo.findByRecipeId(id);
	}
	
	@PutMapping("/comments/")
	public Comment addRecipe(@Valid @RequestBody Comment comment) {
		comment.setPostedDate(new Date());
		return commentRepo.save(comment);
	}

}
