package com.relativerecipes.parser.processor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.relativerecipes.parser.model.RecipeData;

class MetadataProcessorTest extends AbstractProcessorTest {

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
	
	@Test
	void testAllRecipeCookiesSite() throws IOException {
		document = Jsoup.parse(this.getFileContents("all_recipes_cookies.html"));
		RecipeData expectedRecipe = new RecipeData();
		expectedRecipe.setDescription("Crisp edges, chewy middles, and so, so easy to make. Try this wildly-popular chocolate chip cookie recipe for yourself. ");
		expectedRecipe.setName("Best Chocolate Chip Cookies");
		expectedRecipe.setImageUrl("https://imagesvc.meredithcorp.io/v3/mm/image?q=60&c=sc&poi=face&w=960&h=480&url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F43%2F2019%2F12%2Fbest-chocolate-chip-cookies.jpg");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(expectedRecipe, recipe);
	}
	
	@Test
	void testOliverSite() throws IOException {
		document = Jsoup.parse(this.getFileContents("jamie_oliver_cookies.html"));
		RecipeData expectedRecipe = new RecipeData();
		expectedRecipe.setDescription("Discover Jamie Oliver's collection of delicious cookie recipes online today and make the perfect treat that both the kids and the adults will enjoy.");
		expectedRecipe.setName("Cookie Recipes | Jamie Oliver");
		expectedRecipe.setImageUrl("https://cdn.jamieoliver.com/library/images/Jamie-Social.jpg");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(expectedRecipe, recipe);
	}

}
