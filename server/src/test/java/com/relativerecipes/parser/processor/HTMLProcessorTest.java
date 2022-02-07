package com.relativerecipes.parser.processor;

import static org.junit.jupiter.api.Assertions.*;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.relativerecipes.parser.model.RecipeData;

class HTMLProcessorTest extends AbstractProcessorTest {
	

	@BeforeEach
	void setUp() throws Exception {
		processor = new HTMLProcessor();
	}

	@Test
	void test() {
		recipe = new RecipeData();
		document = new Document("");
		assertEquals(new RecipeData(), processor.processPage(document, recipe));
	}

}
