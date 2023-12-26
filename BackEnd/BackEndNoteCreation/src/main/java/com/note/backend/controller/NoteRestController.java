package com.note.backend.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.note.backend.models.entity.Category;
import com.note.backend.models.entity.Note;
import com.note.backend.service.ICategoryService;
import com.note.backend.service.INoteService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "https://noteapp-f9755.web.app" })
@RestController
@RequestMapping("/api")
public class NoteRestController {

	@Autowired
	private INoteService noteService;
	@Autowired
	private ICategoryService categoryService;
	
	@PostMapping("/createCategory")
	public ResponseEntity<?> createCategory(@Valid @RequestBody Category category, BindingResult result) {
		
		Category categoryNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			categoryNew = categoryService.saveCategory(category);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La categoria ha sido creado con éxito!");
		response.put("category", categoryNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}	
	
	
	@GetMapping("/getAllCategory")
	public List<Category> getAllCategory() {
		return categoryService.findAllCategory();
	}
	
	@GetMapping("/note")
	public List<Note> index() {
		return noteService.findAll();
	}
	
	
	
	@GetMapping("/noteID/{id}")
	//@ResponseStatus(HttpStatus.OK)//retorna el codigo 200
	public ResponseEntity<?> show(@PathVariable Long id) { //El ? indica que puede ser cualquier tipo de objeto 		
		Note note = null;
		Map<String, Object> response = new HashMap<>();
		try {
			note = noteService.findById(id);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (note == null) {
			response.put("mensaje", "La nota ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}
	
	@GetMapping("/notesArchived")
	public List<Note> getNotesArchived() {
		return noteService.findByIsArchived();
	}
	
	@GetMapping("/notesActive")
	public List<Note> getNotesActive() {
		return noteService.findByIsActive();
	}
	
	
	@PostMapping("/createNote")
	public ResponseEntity<?> create(@Valid @RequestBody Note note, BindingResult result) {
		
		Note noteNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			note.setIsArchived(1);
			noteNew = noteService.saveNote(note);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La nota ha sido creada con éxito!");
		response.put("nota", noteNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}	
	
	@PutMapping("/noteEdit/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Note note, BindingResult result, @PathVariable Long id) {

		Note noteCurrent = noteService.findById(id);

		Note noteUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (noteCurrent == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			noteCurrent.setTitle(note.getTitle());
			noteCurrent.setDescription(note.getDescription());
			noteCurrent.setIsArchived(note.getIsArchived());
			noteCurrent.setListCategory(note.getListCategory());
			System.out.println("------------->" + note.getIsArchived());
			System.out.println("------------->" + noteCurrent.getIsArchived());

			noteUpdated = noteService.saveNote(noteCurrent);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la nota en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La nota ha sido actualizado con éxito!");
		response.put("nota", noteUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/note/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
		    noteService.deleteNote(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la nota de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La nota se ha eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
}
