package com.qa.bookshelf.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.bookshelf.data.Book;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreate() throws Exception {

		final Book testBook = new Book(null, "title", "author", null, "genre");
		String testBookAsJSON = this.mapper.writeValueAsString(testBook);

		final Book savedBook = new Book(1, "title", "author", null, "genre");
		String savedBookAsJSON = this.mapper.writeValueAsString(savedBook);

		RequestBuilder request = post("/book/create").contentType(MediaType.APPLICATION_JSON).content(testBookAsJSON);

		ResultMatcher checkStatus = status().isCreated();
		ResultMatcher checkContent = content().json(savedBookAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);

	}
}
