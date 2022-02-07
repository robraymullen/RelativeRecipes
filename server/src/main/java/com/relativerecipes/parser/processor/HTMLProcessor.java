package com.relativerecipes.parser.processor;

import org.jsoup.nodes.Document;

import com.relativerecipes.parser.model.RecipeData;

public class HTMLProcessor implements IDocumentProcessor {

	@Override
	public RecipeData processPage(Document document, RecipeData recipeData) {
		return recipeData;
	}

}
