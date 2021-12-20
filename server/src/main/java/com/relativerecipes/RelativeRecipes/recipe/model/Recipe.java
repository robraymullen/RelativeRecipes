package com.relativerecipes.RelativeRecipes.recipe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;

@Entity
public class Recipe {

	@Id
	@GeneratedValue(generator = "recipe_generator")
	@SequenceGenerator(name = "recipe_generator", sequenceName = "recipe_sequence", initialValue = 1)
	private Long id;
	
	@NotBlank
	@Field
	@Size(max=1000)
	String title;
	
	@NotBlank
	@Field
	String text;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "posted_date")
	private Date postedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	
}
