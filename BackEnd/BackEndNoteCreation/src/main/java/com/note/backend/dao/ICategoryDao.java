package com.note.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.note.backend.models.entity.Category;

public interface ICategoryDao  extends JpaRepository<Category, Long> {

}
