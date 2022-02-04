package com.relativerecipes.parser.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.jsoup.nodes.Document;

import com.relativerecipes.parser.model.RecipeData;

public abstract class AbstractProcessorTest {
	
	IDocumentProcessor processor;
	
	RecipeData recipe;
	
	Document document;
	
	protected String getFileContents(String fileName) throws IOException {
		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
		return Files.readString(file.toPath());
	}

}
