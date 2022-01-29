package com.relativerecipes.parser.model;

import java.util.List;

public class RecipeData {

	private String name;
	private String description;
	private List<InstructionStep> instructions;
	private List<String> ingredients;
	private String imageUrl;

	public boolean isComplete() {
		return name != null && description != null && instructions != null && ingredients != null && imageUrl != null;
	}

	/**
	 * Only sets the name if it the name is currently null or empty
	 * 
	 * @param name
	 */
	public void setName(String name) {
		if (this.name == null || this.name.isEmpty()) {
			setName(name, true);
		}
	}

	public void setName(String name, boolean overwrite) {
		if (overwrite || this.name == null || this.name.isEmpty()) {
			this.name = name;
		}
	}

	/**
	 * Only sets the description if it is currently null or empty
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		if (this.description == null || this.description.isEmpty()) {
			setDescription(description, true);
		}
	}

	public void setDescription(String description, boolean overwrite) {
		if (overwrite || this.description == null || this.description.isEmpty()) {
			this.description = description;
		}
	}

	public void setInstructions(List<InstructionStep> instructions) {
		if (this.instructions == null || this.instructions.isEmpty()) {
			setInstructions(instructions, true);
		}
	}

	public void setInstructions(List<InstructionStep> instructions, boolean overwrite) {
		if (overwrite || this.instructions == null || this.instructions.isEmpty()) {
			this.instructions = instructions;
		}
	}

	public void setIngredients(List<String> ingredients) {
		if (this.ingredients == null || this.ingredients.isEmpty()) {
			setIngredients(ingredients, true);
		}
	}

	public void setIngredients(List<String> ingredients, boolean overwrite) {
		if (overwrite || this.ingredients == null || this.ingredients.isEmpty()) {
			this.ingredients = ingredients;
		}
	}

	public void setImageUrl(String imageUrl) {
		if (this.imageUrl == null || this.imageUrl.isEmpty()) {
			setImageUrl(imageUrl, true);
		}
	}

	public void setImageUrl(String imageUrl, boolean overwrite) {
		if (overwrite || this.imageUrl == null || this.imageUrl.isEmpty()) {
			this.imageUrl = imageUrl;
		}
	}

	@Override
	public String toString() {
		return "RecipeData [name=" + name + ", \ndescription=" + description + ", \ninstructions=" + instructions
				+ ", \ningredients=" + ingredients + ", \nimageUrl=" + imageUrl + "]";
	}

}
