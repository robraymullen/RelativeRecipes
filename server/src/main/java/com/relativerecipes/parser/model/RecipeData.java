package com.relativerecipes.parser.model;

import java.util.List;

public class RecipeData {

	private String name;
	private String description;
	private List<InstructionStep> instructions;
	private List<String> ingredients;
	private String imageUrl;

	public boolean isComplete() {
		return this.name != null && !this.name.isEmpty()
				&& this.description != null && !this.description.isEmpty()
				&& this.instructions != null && !this.instructions.isEmpty()
				&& this.ingredients != null && !this.ingredients.isEmpty()
				&& this.imageUrl != null && !this.imageUrl.isEmpty();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ((instructions == null) ? 0 : instructions.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		RecipeData other = (RecipeData) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (instructions == null) {
			if (other.instructions != null)
				return false;
		} else if (!instructions.equals(other.instructions))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
