package com.relativerecipes.parser.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;

import com.relativerecipes.parser.model.RecipeData;

public class HTMLProcessor implements IDocumentProcessor {

	@Override
	public RecipeData processPage(Document document, RecipeData recipeData) {
		Set<Node> nodeMatches = new HashSet<>();
		
		document.traverse( new NodeVisitor() {
			boolean foundInstructions = false;
			boolean foundIngredients = false;
		    @Override
			public void head(Node node, int depth) {
		    	
		    	if (node.childNodes().size() > 0) {
		    		if (node.attr("class").toLowerCase().contains("instruction") && !foundInstructions) {
			    		 foundInstructions = true;
			    	} else foundInstructions = false;
			    	if (node.attr("class").toLowerCase().contains("ingredient") && !foundIngredients) {
			    		 foundIngredients = true;
			    	} else foundIngredients = false;
		    	}
		    }
		    @Override
		    public void tail(Node node, int depth) {
		    	if (foundIngredients || foundInstructions) {
		    		nodeMatches.add(node);
		    	}
		    }
		});
		return recipeData;
	}

}
