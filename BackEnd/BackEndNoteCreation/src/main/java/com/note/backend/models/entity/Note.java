package com.note.backend.models.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="note")
public class Note {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message ="no puede estar vacio")
	@Column(nullable=false)
	private String title;
	
	@NotEmpty(message ="no puede estar vacio")
	@Column(nullable=false)
	private String description;
	
//	@NotEmpty(message ="no puede estar vacio")
//	@Column(nullable=false)
	@Column
	private int isArchived;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "note_category", 
			joinColumns = @JoinColumn(name="note_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name="category_id", referencedColumnName = "idCategory")
			)
	private List<Category> listCategory;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsArchived() {
		return isArchived;
	}

	public void setIsArchived(int isArchived) {
		this.isArchived = isArchived;
	}

	public List<Category> getListCategory() {
		return listCategory;
	}

	public void setListCategory(List<Category> listCategory) {
		this.listCategory = listCategory;
	}
	
	
	

}
