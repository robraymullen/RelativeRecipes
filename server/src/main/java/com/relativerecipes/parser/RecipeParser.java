package com.relativerecipes.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
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
		RecipeData recipeData = new RecipeData();
		recipeData  = getJsonLD(head, recipeData);
		System.out.println(recipeData);
		
		if (!recipeData.isComplete()) {
			//Parse twitter metadata in head
			recipeData = getTwitterMetadata(head, recipeData);
			System.out.println(recipeData.name + " \n"+recipeData.description+" \n"+recipeData.imageUrl);
			
			//Extract ingredients in body
			String ingredients = getIngredientsFromBody(doc.body());
			
			//Extract method in body
			String method = getMethodFromBody(doc.body());
		}
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

	private RecipeData getTwitterMetadata(Element head, RecipeData recipeData) {
		head.childNodes().stream().forEach((node) -> {
			String property = node.attr("property");
			switch(property) {
				case "og:description":
					recipeData.description = node.attr("content");
					break;
				case "og:image":
					recipeData.imageUrl = node.attr("content");
					break;
				case "og:site_name":
					recipeData.name = node.attr("content");
					break;
			}
		});
		return recipeData;
	}

	private RecipeData getJsonLD(Element head, RecipeData recipeData) throws ParseException {
		Elements jsonLd = head.getElementsByAttributeValue("type", "application/ld+json");
		if (jsonLd != null && !jsonLd.isEmpty()) {
			String data = jsonLd.get(0).data();
			JSONObject ld = null;
			try {
				ld = new JSONObject(data);
			} catch (JSONException e) {
				try {
					JSONArray ldArr = new JSONArray(data);
					for (Object obj : ldArr) {
						JSONObject mappedObject = (JSONObject) obj;
						if (mappedObject.has("recipeIngredient")) {
							ld = mappedObject;
						}
					}
				} catch (JSONException error) {
					return recipeData;
				}
			}
			
			if (ld.has("@graph")) { //@graph and @context separated
				Object graph = ld.get("@graph");
				if (graph instanceof JSONArray) {
					JSONArray graphArr = (JSONArray) graph;
					List<JSONObject> extractedJsonData = new ArrayList<>();
					graphArr.forEach((object) -> {
						JSONObject obj = (JSONObject) object;
						if (obj.has("recipeIngredient")) {
							extractedJsonData.add(obj);
						}
					});
					JSONObject jsonData = extractedJsonData.get(0);
					extractAllJSONld(jsonData, recipeData);
				} else {
					
				}
			} else {
				extractAllJSONld(ld, recipeData);
			}
		}
		return recipeData;
	}
	
	private void extractAllJSONld(JSONObject ld, RecipeData data) {
		data.description = ld.getString("description");
		data.ingredients = (List<String>) ld.getJSONArray("recipeIngredient").toList().stream().map((ingredient) -> (String) ingredient).collect(Collectors.toList());
		data.name = ld.getString("name");
		data.instructions = extractJSONInstructions(ld.getJSONArray("recipeInstructions"));
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
	
	private class RecipeData {
		String name;
		String description;
		List<InstructionStep> instructions;
		List<String> ingredients;
		String imageUrl;
		@Override
		public String toString() {
			return "JSONData [name=" + name + ",\n description=" + description + ",\n instructions=" + instructions
					+ ",\n ingredients=" + ingredients + "]";
		}
		
		boolean isComplete() {
			return name != null && description != null && instructions != null && ingredients != null 
					&& imageUrl != null;
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
