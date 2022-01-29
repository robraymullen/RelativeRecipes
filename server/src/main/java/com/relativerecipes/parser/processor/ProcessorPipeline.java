package com.relativerecipes.parser.processor;

import java.util.Stack;

import org.jsoup.nodes.Document;

import com.relativerecipes.parser.model.RecipeData;

public class ProcessorPipeline {
	
	private Stack<IDocumentProcessor> processors = new Stack<>();
	
	
	public ProcessorPipeline() {
		IDocumentProcessor jsonProcessor = new JSONProcessor();
		IDocumentProcessor metaProcessor = new MetadataProcessor();
		processors.add(jsonProcessor);
		processors.add(metaProcessor);
	}
	
	public RecipeData run(Document document, RecipeData recipeData) {
		while (!processors.isEmpty()) {
			processors.pop().processPage(document, recipeData);
		}
		return recipeData;
	}
	
	

}
