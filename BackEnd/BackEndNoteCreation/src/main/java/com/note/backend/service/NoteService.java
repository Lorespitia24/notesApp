package com.note.backend.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.note.backend.dao.INoteDao;
import com.note.backend.models.entity.Note;

@Service
public class NoteService implements INoteService{
	@Autowired
	private INoteDao noteDao;
	

	@Transactional(readOnly = true)
	public Note findById(Long id) {
		return noteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Note> findAll() {
		return  (List<Note>) noteDao.findAll();
	}


	@Override
	@Transactional
	public Note saveNote(Note note) {
		return noteDao.save(note);
	}
	
	@Override
	@Transactional
	public void deleteNote(Long id) {
		noteDao.deleteById(id);
		
	}
	
	@Transactional(readOnly = true)
	public List<Note> findByIsArchived() {

		return noteDao.findByIsArchived();
	}
	
	@Transactional(readOnly = true)
	public List<Note> findByIsActive() {
		return noteDao.findByIsActive();
	}
	
	
	
	
	
	
	
}
