package com.relativerecipes.parser.model;

public class InstructionStep {
	/*
	 * "@type": "HowToStep",
      "name": "Preheat",
      "text": "Preheat the oven to 350 degrees F. Grease and flour a 9x9 inch pan.",
      "url": "https://example.com/party-coffee-cake#step1",
      "image": "https://example.com/photos/party-coffee-cake/step1.jpg"

	 */
	public String name;
	public String text;
	public String url;
	public String image;
	
	@Override
	public String toString() {
		return "InstructionStep [name=" + name + ", text=" + text + ", url=" + url + ", image=" + image + "]";
	}
}