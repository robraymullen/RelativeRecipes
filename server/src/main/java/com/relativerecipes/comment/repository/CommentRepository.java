package com.relativerecipes.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.relativerecipes.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	public List<Comment> findByRecipeId(Long recipeId);

}
