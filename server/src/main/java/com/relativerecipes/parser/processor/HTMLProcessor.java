package com.relativerecipes.parser.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;

import com.relativerecipes.parser.model.InstructionStep;
import com.relativerecipes.parser.model.RecipeData;

public class HTMLProcessor implements IDocumentProcessor {

	@Override
	public RecipeData processPage(Document document, RecipeData recipeData) {
		Set<String> ingredientMatches = new HashSet<>();
		Set<String> instructionMatches = new HashSet<>();
		Set<String> titleSet = new HashSet<>();
		
		document.traverse( new NodeVisitor() {
		    @Override
			public void head(Node node, int depth) {
		    	
		    	if (node.childNodes().size() > 0) {
		    		if (node.attr("class").toLowerCase().contains("instruction")) {
			    		 instructionMatches.add(((Element)node).text());
			    	}
			    	if (node.attr("class").toLowerCase().contains("ingredient")) {
			    		 ingredientMatches.add(((Element)node).text());
			    	}
			    	if ("title".equals(node.nodeName()) || "h1".equals(node.nodeName())) {
			    		titleSet.add(((Element) node).text());
			    	}
		    	}
		    }
		    @Override
		    public void tail(Node node, int depth) {
		    	// Nothing to do
		    }
		});
		
		if (!titleSet.isEmpty()) {
			recipeData.setName(titleSet.toString().replace("[", "").replace("]", ""));
		}
		if (!instructionMatches.isEmpty()) {
			InstructionStep step = new InstructionStep();
			step.text = instructionMatches.toString().replace("[", "").replace("]", "");
			recipeData.setInstructions(List.of(step));	
		}
		if (!ingredientMatches.isEmpty()) {
			recipeData.setIngredients(List.of(ingredientMatches.toString().replaceAll("\\[", "").replaceAll("]", "")));	
		}
		return recipeData;
	}

}
