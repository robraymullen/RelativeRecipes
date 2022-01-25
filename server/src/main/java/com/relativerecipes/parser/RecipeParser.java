package com.relativerecipes.parser;

import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

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
		Metadata meta = getTwitterMetadata(head);
		System.out.println(meta.name + " \n"+meta.description+" \n"+meta.imageUrl);
		
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

	private Metadata getTwitterMetadata(Element head) {
		Metadata meta = new Metadata();
		head.childNodes().stream().forEach((node) -> {
			String property = node.attr("property");
			switch(property) {
				case "og:description":
					meta.description = node.attr("content");
					break;
				case "og:image":
					meta.imageUrl = node.attr("content");
					break;
				case "og:site_name":
					meta.name = node.attr("content");
					break;
			}
		});
		return meta;
	}
	
	private boolean isRecipeMetadata(Node node) {
		String property = node.attr("property");
		return property.contains("og:description")
				|| property.contains("og:image")
				|| property.contains("og:site_name");
	}

	private String getJsonLD(Element head) {
		return null;
	}
	
	private class Metadata {
		String imageUrl;
		String description;
		String name;
		
		boolean isComplete() {
			return imageUrl != null && description != null && name != null;
		}
	}

}
