package com.relativerecipes.scraper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.relativerecipes.parser.RecipeParser;

public class PageFetcher {
	
	private final static HttpClient client = HttpClient.newBuilder()
								  .version(Version.HTTP_1_1)
								  .followRedirects(Redirect.NORMAL)
								  .connectTimeout(Duration.ofSeconds(60))
								  .build();
	
	private static HttpResponse<String> response;
	
	public static void loadPage(String url) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
		response = client.send(request, BodyHandlers.ofString());
	}
	
	public String getContent() {
		return response.body();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		loadPage("https://www.jamieoliver.com/recipes/potato-recipes/basic-latkes/");
		RecipeParser parser = new RecipeParser();
		parser.getRecipeText(response.body());
	}
}
