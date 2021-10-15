package com.qa.bookshelf.service;

import static org.assertj.core.api.Assertions.assertThat;

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
	void testGetById() {
		final Integer Id = 1;
		final Book book = new Book(Id, "Design Patterns", "Freeman", 300, "Educational");

		Mockito.when(this.repo.findById(Id)).thenReturn(Optional.of(book));

		assertThat(this.service.getById(Id)).isEqualTo(book);

		Mockito.verify(this.repo, Mockito.times(1)).findById(Id);
	}

}
