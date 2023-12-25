package com.note.backend.service;

import java.util.List;

import com.note.backend.models.entity.Category;

public interface ICategoryService {

	public Category findCategoryById(Long id);
	public List<Category> findAllCategory();
	public Category saveCategory(Category category);
}
