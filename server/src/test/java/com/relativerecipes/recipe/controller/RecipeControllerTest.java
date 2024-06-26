package com.relativerecipes.recipe.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.relativerecipes.recipe.model.Recipe;
import com.relativerecipes.recipe.repository.RecipeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
		  locations = "classpath:application-integration-test.properties")
@Transactional
public class RecipeControllerTest {
	
	@Autowired
    MockMvc mvc;
	
	@Autowired
	RecipeRepository recipeRepo;
	
	Recipe recipe;
	Date date;
	
	@BeforeEach
	public void setup() {
		recipe = new Recipe();
		recipe.setTitle("Test title");
		recipe.setText("Buy some food, cook it");
		date = new Date();
		recipe.setPostedDate(date);
	}
	
	@AfterEach
	public void tearDown() {
		recipeRepo.deleteAll();
		recipeRepo.flush();
	}

	@Test
	public void testAddRecipe() throws Exception {
		mvc.perform(post("/recipes")
				.content("{\"title\":\"Some new test title\",\"text\":\"some text for a test recipe\"}")
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(1, recipeRepo.count());
	}
	
	@Test
	public void testGetAllRecipes() throws Exception {
		mvc.perform(post("/recipes")
				.content("{\"title\":\"Some new test title\",\"text\":\"some text for a test recipe\"}")
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		mvc.perform(get("/recipes")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").exists())
			      .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").isString())
			      .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").exists())
			      .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").isString())
			      .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
				  .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isString());
	}
	
	@Test
	public void testGetRecipeById() throws Exception {
		mvc.perform(post("/recipes")
				.content("{\"title\":\"Some new test title\",\"text\":\"some text for a test recipe\"}")
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		List<Recipe> recipes = recipeRepo.findAll();
		String id = recipes.get(0).getId();
		
		mvc.perform(get("/recipes/"+id)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.title").exists())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.title").isString())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.text").exists())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.text").isString())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
				  .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString());
		
	}
	
	@Test
	public void testDeleteRecipeById() throws Exception {
		mvc.perform(post("/recipes")
				.content("{\"title\":\"Some new test title\",\"text\":\"some text for a test recipe\"}")
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		List<Recipe> recipes = recipeRepo.findAll();
		String id = recipes.get(0).getId();
		
		mvc.perform(delete("/recipes/"+id)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
		
		Optional<Recipe> recipe = recipeRepo.findById(id);
		assertFalse(recipe.isPresent());
	}
	
	@Test
	public void testRecipeTagsHaveTrimmedWhitespace() throws Exception {
		mvc.perform(post("/recipes")
				.content("{\"title\":\"Some new test title\",\"text\":\"some text for a test recipe\",\"tags\":[\"    first   \"]}")
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		Recipe recipe = recipeRepo.findAll().get(0);
		assertTrue(recipe.getTags().contains("first"));
		
	}

}
