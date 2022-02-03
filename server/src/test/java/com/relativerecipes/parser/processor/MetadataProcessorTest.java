package com.relativerecipes.parser.processor;

import static org.junit.jupiter.api.Assertions.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.relativerecipes.parser.model.RecipeData;

class MetadataProcessorTest {
	
	IDocumentProcessor processor;
	Document document;
	RecipeData recipe;

	@BeforeEach
	void setUp() throws Exception {
		processor = new MetadataProcessor();
	}

	@Test
	void testEmptyDocument() {
		document = Jsoup.parse("");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(new RecipeData(), recipe);
	}
	
	@Test
	void testEmptyHeadNode() {
		document = Jsoup.parse("<html><head></head></html>");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(new RecipeData(), recipe);
	}
	
	@Test
	void testEmptyMetadataNodes() {
		/*
		 *  <meta property="og:image" content="image.jpg"> 
 <meta property="og:site_name" content="website">
 <meta property="og:title" content="title"> 
 <meta property="og:description" content="description"> 
		 */
		document = Jsoup.parse("<html><head>"
				+ "<meta></head></html>");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(new RecipeData(), recipe);
	}
	
	@Test
	void testContentPropertyMissing() {
		document = Jsoup.parse("<html><head>"
				+ "<meta property=\"og:site_name\"></head></html>");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(new RecipeData(), recipe);
	}
	
	@Test
	void testContentPropertyEmpty() {
		document = Jsoup.parse("<html><head>"
				+ "<meta property=\"og:site_name\" content=\"\"></head></html>");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(new RecipeData(), recipe);
	}
	
	@Test
	void testContentPropertiesSet() {
		document = Jsoup.parse("<html><head>"
				+ "<meta property=\"og:image\" content=\"image.jpg\"> "
				+ "<meta property=\"og:title\" content=\"title\">"
				+ "<meta property=\"og:description\" content=\"description\"></head></html>");
		recipe = processor.processPage(document, new RecipeData());
		RecipeData expectedRecipe = new RecipeData();
		expectedRecipe.setName("title");
		expectedRecipe.setDescription("description");
		expectedRecipe.setImageUrl("image.jpg");
		assertEquals(expectedRecipe, recipe);
	}

}
