package com.relativerecipes.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class RecipeParser {
	
	private Document doc;
	
	public String getRecipeText(String content) throws ParseException {
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
		JSONData jsonLD  = getJsonLD(head);
		System.out.println(jsonLD);
		
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

	private JSONData getJsonLD(Element head) throws ParseException {
		Elements jsonLd = head.getElementsByAttributeValue("type", "application/ld+json");
		if (jsonLd != null) {
			String data = jsonLd.get(0).data();
			JSONObject ld = new JSONObject(data);
			JSONData json = new JSONData();
			if (ld.has("@graph")) { //@graph and @context separated
				Object graph = ld.get("@graph");
				if (graph instanceof JSONArray) {
					JSONArray graphArr = (JSONArray) graph;
					List<JSONObject> recipeData = new ArrayList<>();
					graphArr.forEach((object) -> {
						JSONObject obj = (JSONObject) object;
						if (obj.has("recipeIngredient")) {
							recipeData.add(obj);
						}
					});
					JSONObject jsonData = recipeData.get(0);
					json.description = jsonData.getString("description");
					json.ingredients = (List<String>) jsonData.getJSONArray("recipeIngredient").toList().stream().map((ingredient) -> (String) ingredient).collect(Collectors.toList());
					json.name = jsonData.getString("name");
					json.instructions = extractJSONInstructions(jsonData.getJSONArray("recipeInstructions"));
				} else {
					
				}
			} else {
				
			}
			return json;
		}
		return null;
	}
	
	private List<InstructionStep> extractJSONInstructions(JSONArray json) {
		List<InstructionStep> instructions = new ArrayList<>();
		json.forEach((instruction) -> {
			JSONObject jsonInstruction = (JSONObject) instruction;
			InstructionStep step = new InstructionStep();
			step.name = jsonInstruction.has("name") ? jsonInstruction.getString("name") : "";
			step.image = jsonInstruction.has("image") ? jsonInstruction.getString("image") : "";
			step.text = jsonInstruction.has("text") ? jsonInstruction.getString("text") : "";
			step.url = jsonInstruction.has("url") ? jsonInstruction.getString("url") : "";
			instructions.add(step);
		});
		return instructions;
	}
	
	private class Metadata {
		String imageUrl;
		String description;
		String name;
		
		boolean isComplete() {
			return imageUrl != null && description != null && name != null;
		}
	}
	
	private class JSONData {
		String name;
		String description;
		List<InstructionStep> instructions;
		List<String> ingredients;
		@Override
		public String toString() {
			return "JSONData [name=" + name + ", description=" + description + ", instructions=" + instructions
					+ ", ingredients=" + ingredients + "]";
		}

	}
	
	private class InstructionStep {
		/*
		 * "@type": "HowToStep",
          "name": "Preheat",
          "text": "Preheat the oven to 350 degrees F. Grease and flour a 9x9 inch pan.",
          "url": "https://example.com/party-coffee-cake#step1",
          "image": "https://example.com/photos/party-coffee-cake/step1.jpg"

		 */
		String name;
		String text;
		String url;
		String image;
		
		@Override
		public String toString() {
			return "InstructionStep [name=" + name + ", text=" + text + ", url=" + url + ", image=" + image + "]";
		}
	}

}
