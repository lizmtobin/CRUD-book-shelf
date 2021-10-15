package com.qa.bookshelf.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.bookshelf.data.Book;
import com.qa.bookshelf.repo.BookRepo;
import com.qa.bookshelf.services.BookService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class ServiceUnitTest {
	@Autowired
	private BookService service;

	@MockBean
	private BookRepo repo;

	@Test
	void testCreate() {
		Book newBook = new Book(null, "Design Patterns", "Freeman", 300, "Educational");
		Book savedBook = new Book(1, "Design Patterns", "Freeman", 300, "Educational");

		Mockito.when(this.repo.save(newBook)).thenReturn(savedBook);

		assertThat(this.service.create(newBook)).isEqualTo(savedBook);

		Mockito.verify(this.repo, Mockito.times(1)).save(newBook);
	}

	@Test
	void testGetById() {
		final Integer Id = 1;
		final Book book = new Book(Id, "Design Patterns", "Freeman", 300, "Educational");

		Mockito.when(this.repo.findById(Id)).thenReturn(Optional.of(book));

		assertThat(this.service.getById(Id)).isEqualTo(book);

		Mockito.verify(this.repo, Mockito.times(1)).findById(Id);
	}

	@Test
	void testGetAll() {
		final List<Book> books = List.of(new Book(1, "Design Patterns", "Freeman", 300, "Educational"),
				new Book(2, "Design Patterns", "Freeman", 300, "Educational"));

		Mockito.when(this.repo.findAll()).thenReturn(books);

		assertThat(this.service.getAll()).isEqualTo(books);

		Mockito.verify(this.repo, Mockito.times(1)).findAll();
	}

	@Test
	void testUpdate() {

		final Integer id = 1;

		Book book = new Book(id, "Design Patterns", "Freeman", 300, "Educational");
		Optional<Book> optionalBook = Optional.of(book);

		Book newBook = new Book(id, "Design Patterns", "Freeman", 300, "Educational");

		Mockito.when(this.repo.findById(id)).thenReturn(optionalBook);
		Mockito.when(this.repo.save(newBook)).thenReturn(newBook);

		assertThat(this.service.updateBook(book.getId(), newBook)).isEqualTo(newBook);

		Mockito.verify(this.repo, Mockito.times(1)).findById(id);
		Mockito.verify(this.repo, Mockito.times(1)).save(newBook);
	}

	@Test
	void testDelete() {
		final Integer id = 1;

		Mockito.when(this.repo.existsById(id)).thenReturn(false);

		assertThat(this.service.removeBook(id)).isEqualTo(true);

		Mockito.verify(this.repo, Mockito.times(1)).existsById(id);
	}

}
