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

public class PageFetcher implements HTTPFetcher {
	
	private final HttpClient client = HttpClient.newBuilder()
								  .version(Version.HTTP_1_1)
								  .followRedirects(Redirect.NORMAL)
								  .connectTimeout(Duration.ofSeconds(60))
								  .build();
	
	private HttpResponse<String> response;
	
	@Override
	public HTTPFetcher loadPage(String url) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
		response = client.send(request, BodyHandlers.ofString());
		return this;
	}
	
	@Override
	public String getContent() {
		return response.body();
	}
}
