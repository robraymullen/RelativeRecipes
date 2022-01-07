package com.relativerecipes.comment.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Field;
import org.springframework.data.annotation.CreatedDate;

import com.relativerecipes.recipe.model.Recipe;

@Entity
public class Comment {

	@Id
	@GeneratedValue(generator = "comment_generator")
	@SequenceGenerator(name = "comment_generator", sequenceName = "comment_sequence", initialValue = 1)
	private Long id;
	
	@NotBlank
	@Field
	@Size(max=5000)
	private String text;
	
	@NotBlank
	@Field
	@Size(max=500)
	private String author;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;
	
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
}
