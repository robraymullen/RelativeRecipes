package com.relativerecipes.parser.processor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.relativerecipes.parser.model.RecipeData;

public class MetadataProcessor implements IDocumentProcessor {

	@Override
	public RecipeData processPage(Document document, RecipeData recipeData) {
		Element head = document.head();
		head.childNodes().stream().forEach((node) -> {
			String property = node.attr("property");
			String content = node.attr("content");
			switch(property) {
				case "og:description":
					recipeData.setDescription(content);
					break;
				case "og:image":
					recipeData.setImageUrl(content);
					break;
				case "og:site_name":
					recipeData.setName(content);
					break;
			}
		});
		return recipeData;
	}
}
