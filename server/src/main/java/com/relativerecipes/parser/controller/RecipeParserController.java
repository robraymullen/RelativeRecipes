package com.relativerecipes.parser.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.relativerecipes.parser.RecipeParser;
import com.relativerecipes.parser.model.RecipeData;
import com.relativerecipes.scraper.PageFetcher;

import net.minidev.json.parser.ParseException;

@Controller
public class RecipeParserController {

	@PostMapping("/recipe/parser")
	public String getRecipeData(@Valid @RequestBody String recipeUrl) throws IOException, InterruptedException, ParseException {
		PageFetcher.loadPage(recipeUrl);
		String pageContent = PageFetcher.getContent();
		RecipeParser parser = new RecipeParser();
		return parser.getRecipeText(pageContent);
	}
}
