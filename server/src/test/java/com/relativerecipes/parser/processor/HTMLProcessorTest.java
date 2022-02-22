package com.relativerecipes.parser.processor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.relativerecipes.parser.model.InstructionStep;
import com.relativerecipes.parser.model.RecipeData;

class HTMLProcessorTest extends AbstractProcessorTest {
	

	@BeforeEach
	void setUp() throws Exception {
		processor = new HTMLProcessor();
	}

	@Test
	void testEmptyHTMLDocument() {
		recipe = new RecipeData();
		document = new Document("");
		RecipeData expectedRecipe = new RecipeData();
		assertEquals(expectedRecipe, processor.processPage(document, recipe));
	}
	
	@Test
	void testBasicHTMLDocument() throws IOException {
		document = Jsoup.parse(getFileContents("basic_page.html"));
		recipe = new RecipeData();
		recipe = processor.processPage(document, recipe);
		RecipeData expectedRecipe = new RecipeData();
		expectedRecipe.setName("Header");
		expectedRecipe.setIngredients(List.of("Header Text1 Text2"));
		InstructionStep step = new InstructionStep();
		step.text="Header Text1 Text2";
		expectedRecipe.setInstructions(List.of(step));
		assertEquals(expectedRecipe, recipe);
	}

}
