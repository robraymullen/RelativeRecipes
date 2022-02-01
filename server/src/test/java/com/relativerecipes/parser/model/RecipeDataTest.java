package com.relativerecipes.parser.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeDataTest {

	RecipeData recipe = new RecipeData();
	
	@BeforeEach
	void setUp() throws Exception {
		recipe.setName("Name");
		recipe.setDescription("description");
		recipe.setImageUrl("url");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testIsCompleteWithPartDataSet() {
		assertFalse(recipe.isComplete());
	}
	
	@Test
	void testIsCompleteWithoutDataSet() {
		recipe = new RecipeData();
		assertFalse(recipe.isComplete());
	}
	
	@Test
	void testIsCompleteWithEmptyData() {
		recipe.setName("", true);
		recipe.setDescription("", true);
		recipe.setImageUrl("", true);
		recipe.setIngredients(new ArrayList<String>());
		recipe.setInstructions(new ArrayList<InstructionStep>());
		assertFalse(recipe.isComplete());
	}
	
	@Test
	void testIsCompleteWithAllDataSet() {
		List<String> ingredients = new ArrayList<>();
		ingredients.add("ingredient");
		recipe.setIngredients(ingredients);
		InstructionStep step = new InstructionStep();
		step.text = "text";
		List<InstructionStep> instructions = new ArrayList<>();
		instructions.add(step);
		recipe.setInstructions(instructions);
		assertTrue(recipe.isComplete());
	}

}
