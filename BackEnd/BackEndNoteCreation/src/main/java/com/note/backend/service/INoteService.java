package com.note.backend.service;

import java.util.List;


import com.note.backend.models.entity.Note;

public interface INoteService {
	public Note findById(Long id);
	public List<Note> findAll() ;
	public Note saveNote(Note note);
	public void deleteNote(Long id);
	public List<Note> findByIsArchived();
	public List<Note> findByIsActive();
	
}
