package com.relativerecipes.parser.processor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

import org.jsoup.nodes.Document;

import com.relativerecipes.parser.model.RecipeData;

public class ProcessorPipeline {
	
	private Queue<IDocumentProcessor> processors = new ArrayDeque<>();
	
	
	public ProcessorPipeline() {
		IDocumentProcessor jsonProcessor = new JSONProcessor();
		IDocumentProcessor metaProcessor = new MetadataProcessor();
		processors.add(jsonProcessor);
		processors.add(metaProcessor);
	}
	
	public RecipeData run(Document document, RecipeData recipeData) {
		while (!processors.isEmpty()) {
			IDocumentProcessor processor = processors.remove();
			processor.processPage(document, recipeData);
		}
		return recipeData;
	}
	
	

}
