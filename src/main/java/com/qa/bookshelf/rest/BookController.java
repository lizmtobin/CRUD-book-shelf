package com.qa.bookshelf.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.bookshelf.data.Book;
import com.qa.bookshelf.services.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	private BookService service;

	// constructor & dependency injection

	public BookController(BookService service) {
		super();
		this.service = service;
	}

	// web integration
	@PostMapping("/create")
	public ResponseEntity<Book> create(@RequestBody Book newBook) {
		return new ResponseEntity<Book>(this.service.create(newBook), HttpStatus.CREATED);
	}

	@GetMapping("/get/{id}")
	public Book getById(@PathVariable Integer id) {
		return this.service.getById(id);
	}

	@GetMapping("/books")
	public List<Book> getAll() {
		return this.service.getAll();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Book> updateBook(@RequestBody Book newBook, @PathVariable Integer id) {
		return new ResponseEntity<Book>(this.service.updateBook(id, newBook), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<?> removeBook(@PathVariable Integer id) {
		return this.service.removeBook(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
