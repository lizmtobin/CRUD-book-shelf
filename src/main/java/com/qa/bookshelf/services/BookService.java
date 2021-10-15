package com.qa.bookshelf.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qa.bookshelf.data.Book;
import com.qa.bookshelf.exceptions.BookNotFoundException;
import com.qa.bookshelf.repo.BookRepo;

@Service
public class BookService {

	private BookRepo repo;

	public BookService(BookRepo repo) {
		super();
		this.repo = repo;
	}

	public Book create(Book newBook) {
		return this.repo.save(newBook);
	}

	public Book getById(Integer id) {
		return this.repo.findById(id).orElseThrow(BookNotFoundException::new);
	}

	public List<Book> getAll() {
		return this.repo.findAll();
	}

	public Book updateBook(Integer id, Book newBook) {
		Book oldBook = this.repo.findById(id).orElseThrow(BookNotFoundException::new);
		;
		oldBook.setTitle(newBook.getTitle());
		oldBook.setAuthor(newBook.getAuthor());
		oldBook.setPages(newBook.getPages());
		oldBook.setGenre(newBook.getGenre());
		return this.repo.save(oldBook);
	}

	public boolean removeBook(Integer id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
