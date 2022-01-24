package com.relativerecipes.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RecipeParser {
	
	private Document doc;
	
	public String getRecipeText(String content) {
		/*
		 * Ideally the recipe data will just be available in a json-ld within the html head
		 * However this isn't always the case.
		 * 
		 * If not we'll look for the twitter metadata in the head to (hopefully) get some rich data
		 * 
		 * Then we'll traverse the html body and look for elements with a method class or ingredients class
		 * This final part will need to evolve over time to handle more difficult cases
		 */
		
		//Parse JSON-ld data in head
		doc = Jsoup.parse(content);
		Element head = doc.head();
		String jsonLD  = getJsonLD(head);
		
		//Parse twitter metadata in head
		String twitterMetadata = getTwitterMetadata(head);
		
		//Extract ingredients in body
		String ingredients = getIngredientsFromBody(doc.body());
		
		//Extract method in body
		String method = getMethodFromBody(doc.body());
		return null;
	}
	
	private String getMethodFromBody(Element body) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getIngredientsFromBody(Element body) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getTwitterMetadata(Element head) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getJsonLD(Element head) {
		return null;
	}

}
