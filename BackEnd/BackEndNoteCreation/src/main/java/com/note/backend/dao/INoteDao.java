package com.note.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.note.backend.models.entity.Note;


public interface INoteDao  extends JpaRepository<Note, Long> {
	
	@Query(value = "select * from railway.note n where n.is_archived =0 ", nativeQuery = true)
	List<Note> findByIsArchived();
	
	@Query(value = "select * from railway.note n where n.is_archived =1 ", nativeQuery = true)
	List<Note> findByIsActive();
	
	
	
	
}
