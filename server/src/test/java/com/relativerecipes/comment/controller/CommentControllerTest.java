package com.relativerecipes.comment.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.relativerecipes.comment.model.Comment;
import com.relativerecipes.comment.repository.CommentRepository;
import com.relativerecipes.recipe.model.Recipe;
import com.relativerecipes.recipe.repository.RecipeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
		  locations = "classpath:application-integration-test.properties")
@Transactional
public class CommentControllerTest {
	
	@Autowired
    MockMvc mvc;
	
	@Autowired
	CommentRepository commentRepo;
	
	@Autowired
	RecipeRepository recipeRepo;
	
	Comment comment;
	Recipe recipe;
	Date date;
	
	@BeforeEach
	public void setup() {
		recipe = new Recipe();
		recipe.setPostedDate(new Date());
		recipe.setTitle("Test title");
		recipe.setText("recipe text");
		comment = new Comment();
		comment.setText("A test comment");
		comment.setPostedDate(new Date());
		comment.setAuthor("TestUser");
		comment.setRecipe(recipe);
		recipe.getComments().add(comment);
	}
	
	@AfterEach
	public void tearDown() {
		commentRepo.deleteAll();
		commentRepo.flush();
		recipeRepo.deleteAll();
		recipeRepo.flush();
	}

	@Test
	public void testAddComment() throws Exception {
		mvc.perform(put("/recipes")
				.content("{\"title\":\"Some new test title\",\"text\":\"some text for a test recipe\"}")
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		Recipe recipe = recipeRepo.findAll().get(0);
		mvc.perform(put("/comments/recipe/"+recipe.getId())
				.content("{\"text\":\"A test comment\",\"author\":\"TestUser\"}")
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		assertEquals(1, commentRepo.count());
		assertEquals(1, recipeRepo.findAll().get(0).getComments().size());
	}
	
	@Test()
	public void testAddCommentWithoutRecipeThrowsError() throws Exception {
		mvc.perform(put("/comments/recipe/0")
				.content("{\"text\":\"A test comment\",\"author\":\"TestUser\"}")
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		assertEquals(0, commentRepo.count());
	}

}
