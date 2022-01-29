package com.relativerecipes.parser.processor;

import org.jsoup.nodes.Document;

import com.relativerecipes.parser.model.RecipeData;

public interface IDocumentProcessor {
	
	RecipeData processPage(Document document, RecipeData recipeData);

}
