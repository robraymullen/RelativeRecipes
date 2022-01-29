package com.relativerecipes.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.relativerecipes.parser.model.RecipeData;
import com.relativerecipes.parser.processor.ProcessorPipeline;

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
		RecipeData recipeData = new RecipeData();
		ProcessorPipeline pipeline = new ProcessorPipeline();
		pipeline.run(doc, recipeData);
		
		System.out.println(recipeData);
		return null;
		
	}
}
