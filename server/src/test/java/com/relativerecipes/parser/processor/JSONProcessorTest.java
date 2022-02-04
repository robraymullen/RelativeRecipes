package com.relativerecipes.parser.processor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.relativerecipes.parser.model.InstructionStep;
import com.relativerecipes.parser.model.RecipeData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JSONProcessorTest extends AbstractProcessorTest {
	
	@BeforeEach
	void setUp() throws Exception {
		processor = new JSONProcessor();
	}

	@Test
	void testEmptyDocument() {
		document = Jsoup.parse("");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(new RecipeData(), recipe);
	}
	
	@Test
	void testEmptyJSONld() {
		document = Jsoup.parse("<html><head><script type='application/ld+json'/></head></html>");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(new RecipeData(), recipe);
	}
	
	@Test
	void testEmptyJSONObject() {
		document = Jsoup.parse("<html><head><script type='application/ld+json'/>{}</head></html>");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(new RecipeData(), recipe);
	}
	
	@Test
	void testEmptyJSONArray() {
		document = Jsoup.parse("<html><head><script type='application/ld+json'/>[]</head></html>");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(new RecipeData(), recipe);
	}
	
	@Test
	void testInvalidJSONObject() {
		document = Jsoup.parse("<html><head><script type='application/ld+json'/>{'recipeIngredient': [ingredient], 'recipeInstructions': {</head></html>");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(new RecipeData(), recipe);
	}
	
	@Test
	void testInvalidJSONArray() {
		document = Jsoup.parse("<html><head><script type='application/ld+json'/>{'recipeIngredient':['ingredient'}</head></html>");
		recipe = processor.processPage(document, new RecipeData());
		assertEquals(new RecipeData(), recipe);
	}
	
	@Test
	void testNestedArrays() {
		JSONObject json = new JSONObject();
		JSONArray array1 = new JSONArray();
		JSONObject innerElem1 = new JSONObject();
		JSONArray innerArray1 = new JSONArray();
		JSONObject innerObject = new JSONObject();
		JSONArray recipeArray = new JSONArray();
		recipeArray.put("Ingredient text");
		innerObject.put("recipeIngredient", recipeArray);
		innerElem1.put("innerElem1", innerObject);
		array1.put(innerElem1);
		json.put("nestedArray", array1);
		
		document = Jsoup.parse("<html><head><script type='application/ld+json'>"+json.toString()+"</script></head></html>");
		RecipeData expectedRecipe = new RecipeData();
		List<String> ingredients = new ArrayList<>();
		ingredients.add("Ingredient text");
		expectedRecipe.setIngredients(ingredients);
		expectedRecipe.setName("");
		expectedRecipe.setDescription("");
		expectedRecipe.setInstructions(new ArrayList<>());
		recipe = new RecipeData();
		recipe = processor.processPage(document, recipe);
		assertEquals(expectedRecipe, recipe);
	}
	
	/**
	 * No json ld
	 * @throws IOException
	 */
	@Test
	void testProcessorJamieOliverPage() throws IOException {
		document = Jsoup.parse(getFileContents("jamie_oliver_cookies.html"));
		recipe = new RecipeData();
		recipe = processor.processPage(document, recipe);
		assertEquals(new RecipeData(), recipe);
		
	}
	
	@Test
	void testProcessorAllRecipesPage() throws IOException {
		document = Jsoup.parse(getFileContents("all_recipes_cookies.html"));
		recipe = new RecipeData();
		RecipeData expectedRecipe = new RecipeData();
		expectedRecipe.setName("Best Chocolate Chip Cookies");
		expectedRecipe.setDescription("Crisp edges, chewy middles.");
		List<String> ingredients = new ArrayList<>();
		ingredients.add("1 cup butter, softened");
		ingredients.add("1 cup white sugar");
		ingredients.add("1 cup packed brown sugar");
		ingredients.add("2 eggs");
		ingredients.add("2 teaspoons vanilla extract");
		ingredients.add("1 teaspoon baking soda");
		ingredients.add("2 teaspoons hot water");
		ingredients.add("½ teaspoon salt");
		ingredients.add("3 cups all-purpose flour");
		ingredients.add("2 cups semisweet chocolate chips");
		ingredients.add("1 cup chopped walnuts");
		List<InstructionStep> instructions = new ArrayList<>();
		InstructionStep one = new InstructionStep();
		one.name = "";
		one.image = "";
		one.url = "";
		one.text = "Preheat oven to 350 degrees F (175 degrees C).";
		InstructionStep two = new InstructionStep();
		two.name = "";
		two.image = "";
		two.url = "";
		two.text = "Cream together the butter, white sugar, and brown sugar until smooth. Beat in the eggs one at a time, then stir in the vanilla. Dissolve baking soda in hot water.  Add to batter along with salt. Stir in flour, chocolate chips, and nuts. Drop by large spoonfuls onto ungreased pans.";
		InstructionStep three = new InstructionStep();
		three.name = "";
		three.image = "";
		three.url = "";
		three.text = "Bake for about 10 minutes in the preheated oven, or until edges are nicely browned.";
		instructions.add(one);
		instructions.add(two);
		instructions.add(three);
		expectedRecipe.setIngredients(ingredients);
		expectedRecipe.setInstructions(instructions);
		recipe = processor.processPage(document, recipe);
		assertEquals(expectedRecipe, recipe);
		
	}
}
