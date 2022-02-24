package com.relativerecipes.parser.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.relativerecipes.parser.RecipeParser;
import com.relativerecipes.parser.model.RecipeData;
import com.relativerecipes.parser.model.RecipeRequest;
import com.relativerecipes.recipe.repository.RecipeRepository;

import net.minidev.json.parser.ParseException;

@RestController
public class RecipeParserController {
	
	RestTemplate template;
	
	public RecipeParserController(RestTemplateBuilder templateBuilder) {
		this.template = templateBuilder.build();
	}

	@PostMapping("/recipe/parser")
	public RecipeData getRecipeData(@Valid @RequestBody RecipeRequest recipeUrl) throws ParseException  {
		String response = template.getForObject(recipeUrl.getUrl(), String.class);
		RecipeParser parser = new RecipeParser();
		RecipeData data = parser.getRecipeText(response);
		return data;
	}
}
