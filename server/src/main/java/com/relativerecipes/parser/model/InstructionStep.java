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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstructionStep other = (InstructionStep) obj;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	
}