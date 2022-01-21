package com.relativerecipes.recipe.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.relativerecipes.recipe.model.Recipe;
import com.relativerecipes.recipe.repository.RecipeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integration-test.properties")
public class RecipeRepositoryTest extends AbstractRepositoryIntegrationTest {

	@Autowired
	RecipeRepository recipeRepo;
	
	Recipe recipe;
	
	Date date;
	
	@Before
	public void setUp() throws Exception {
		recipe = new Recipe();
		recipe.setTitle("Test title");
		recipe.setText("Buy some food, cook it");
		date = new Date();
		recipe.setPostedDate(date);
	}

	@Rollback
	@After
	public void tearDown() throws Exception {
	}

	@Transactional
	@Test
	public void testGetSingleRecipeById() {
		persist(recipe);
		List<Recipe> recipes = recipeRepo.findAll();
		String id = recipes.get(0).getId();
		Optional<Recipe> recipeById = recipeRepo.findById(id);
		assertEquals(id, recipeById.get().getId());
	}
	
	@Transactional
	@Test
	public void testPersistedProperties() {
		persist(recipe);
		List<Recipe> recipes = recipeRepo.findAll();
		Recipe recipe = recipes.get(0);
		assertEquals("Test title", recipe.getTitle());
		assertEquals("Buy some food, cook it", recipe.getText());
		assertEquals(this.date, recipe.getPostedDate());
	}
	
	@Transactional
	@Test
	public void testTagsHaveNoDuplicates() {
		Set<String> tags = new HashSet<>();
		tags.add("first");
		tags.add("second");
		tags.add("first");
		recipe.setTags(tags);
		persist(recipe);
		List<Recipe> recipes = recipeRepo.findAll();
		Recipe recipe = recipes.get(0);
		assertEquals(2, recipe.getTags().size());
	}

}
