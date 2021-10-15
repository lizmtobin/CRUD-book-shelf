package com.qa.bookshelf.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.bookshelf.data.Book;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:book-schema.sql",
		"classpath:book-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)

public class BookIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreate() throws Exception {

		final Book testBook = new Book(null, "title", "author", null, "genre");
		String testBookAsJSON = this.mapper.writeValueAsString(testBook);

		final Book savedBook = new Book(2, "title", "author", null, "genre");
		String savedBookAsJSON = this.mapper.writeValueAsString(savedBook);

		RequestBuilder request = post("/book/create").contentType(MediaType.APPLICATION_JSON).content(testBookAsJSON);

		ResultMatcher checkStatus = status().isCreated();
		ResultMatcher checkContent = content().json(savedBookAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);

	}

	@Test
	void testGetAll() throws Exception {
		String savedBookAsJSON = this.mapper
				.writeValueAsString(List.of(new Book(1, "Design Patterns", "Freeman", 300, "Educational")));

		RequestBuilder request = get("/book/books");

		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkContent = content().json(savedBookAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);
	}

	@Test
	void testGetById() throws Exception {
		final Book savedBook = new Book(1, "Design Patterns", "Freeman", 300, "Educational");
		String savedBookAsJSON = this.mapper.writeValueAsString(savedBook);

		RequestBuilder request = get("/book/get/" + savedBook.getId());

		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkContent = content().json(savedBookAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);
	}

	@Test
	void testUpdate() throws Exception {
		final Book testBook = new Book(1, "Design Patterns", "Freeman", 300, "Educational");
		final String testBookAsJSON = this.mapper.writeValueAsString(testBook);

		RequestBuilder request = put("/book/update/1").contentType(MediaType.APPLICATION_JSON).content(testBookAsJSON);

		ResultMatcher checkStatus = status().isAccepted();
		ResultMatcher checkContent = content().json(testBookAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);
	}

	@Test
	void testDelete() throws Exception {
		this.mvc.perform(delete("/book/remove/1")).andExpect(status().isNoContent());
	}

	void testCreateAbrdiged() throws Exception {
		final String testBookAsJSON = this.mapper
				.writeValueAsString(new Book(null, "Design Patterns", "Freeman", 300, "Educational"));
		final String savedBookAsJSON = this.mapper
				.writeValueAsString(new Book(1, "Design Patterns", "Freeman", 300, "Educational"));

		this.mvc.perform(post("/book/create").contentType(MediaType.APPLICATION_JSON).content(testMarsupialAsJSON))
				.andExpect(status().isCreated()).andExpect(content().json(savedMarsupialAsJSON));

	}

}
