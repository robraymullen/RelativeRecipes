package com.relativerecipes.scraper;

import java.io.IOException;

public interface HTTPFetcher {
	
	HTTPFetcher loadPage(String url) throws IOException, InterruptedException ;
	
	String getContent();

}
