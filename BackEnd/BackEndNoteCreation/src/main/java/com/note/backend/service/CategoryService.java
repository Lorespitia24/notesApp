package com.note.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.note.backend.dao.ICategoryDao;
import com.note.backend.models.entity.Category;
import com.note.backend.models.entity.Note;


@Service
public class CategoryService implements ICategoryService{
	
	@Autowired
	private ICategoryDao categoryDao;
	
	@Transactional(readOnly = true)
	public Category findCategoryById(Long id) {
		return categoryDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Category> findAllCategory() {
		return  (List<Category>) categoryDao.findAll();
	}

	@Override
	@Transactional
	public Category saveCategory(Category category) {
		return categoryDao.save(category);
	}
	
	

}
