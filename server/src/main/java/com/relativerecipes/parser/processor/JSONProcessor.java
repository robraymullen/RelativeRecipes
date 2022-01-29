package com.relativerecipes.parser.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.relativerecipes.parser.model.InstructionStep;
import com.relativerecipes.parser.model.RecipeData;

public class JSONProcessor implements IDocumentProcessor {

	@Override
	public RecipeData processPage(Document document, RecipeData recipeData) {
		Element head = document.head();
		Elements jsonLd = head.getElementsByAttributeValue("type", "application/ld+json");
		if (!jsonLd.isEmpty()) {
			String data = jsonLd.get(0).data();
			extractAllJSONld(getJSONRecipeData(data), recipeData);
		}
		
		return null;
	}
	
	private void extractAllJSONld(JSONObject ld, RecipeData data) {
		if (ld == null) return;
		data.setDescription(ld.getString("description"));
		data.setIngredients((List<String>) ld.getJSONArray("recipeIngredient").toList().stream().map((ingredient) -> (String) ingredient).collect(Collectors.toList()));
		data.setName(ld.getString("name"));
		data.setInstructions(extractJSONInstructions(ld.getJSONArray("recipeInstructions")));
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
	
	private JSONObject getJSONRecipeData(String data) {
		try {
			JSONObject json = new JSONObject(data);
			return jsonTraversal(json);
		} catch (Exception e) {
			try {
				JSONArray jsonArray = new JSONArray(data);
				return jsonArrayTraversal(jsonArray);
			} catch (Exception f) {
				return null;
			}
		}
	}
	
	private JSONObject jsonTraversal(JSONObject json) {
		JSONObject result = null;
		Iterator<String> keys = json.keys();
		if (json.has("recipeIngredient")) {
			return json;
		}
		while (keys.hasNext()) {
			String key = keys.next();
			Object jsonEntry = json.get(key);
			if (jsonEntry instanceof JSONObject) {
				JSONObject traversedResult = jsonTraversal((JSONObject) jsonEntry);
				return traversedResult;
			} else if (jsonEntry instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) jsonEntry;
				return jsonArrayTraversal(jsonArray);
			}
		}
		
		return result;
	}
	
	private JSONObject jsonArrayTraversal(JSONArray jsonArray) {
		for (int i=0;i<jsonArray.length();i++) {
			if (jsonArray.get(i) instanceof JSONObject) {
				JSONObject innerObject = (JSONObject) jsonArray.get(i);
				JSONObject checkResult = jsonTraversal(innerObject);
				if (checkResult != null) {
					return checkResult;
				}
			}
		}
		return null;
	}

}
