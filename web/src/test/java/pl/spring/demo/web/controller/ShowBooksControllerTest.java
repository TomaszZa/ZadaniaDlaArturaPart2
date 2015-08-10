package pl.spring.demo.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.spring.demo.service.BookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class ShowBooksControllerTest {

	@Autowired
	private BookService bookService;
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		Mockito.reset(bookService); // resetuje starego mocka
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build(); // tworzy
																				// nowego
																				// mocka
	}

	@Test
	public void testShouldDeleteBookWithGoodId() throws Exception {
		// when
		ResultActions response = this.mockMvc.perform(get("/DeletingInformationPage/{id}", 3L)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
		// then

		Mockito.verify(bookService).deleteBookByID(3L);
		Mockito.verify(bookService).findTitleById(3L);
		Mockito.verifyNoMoreInteractions(bookService);

		response.andExpect(status().isOk()).andExpect(view().name("DeletingInformationPage"))
				.andExpect(model().attribute("booksCount", 1));
		// .andExpect(model().attribute("title", "Trzecia książka"));
	}

	@Test
	public void testShouldNotDeleteBookWithBadId() throws Exception {
		// when
		Mockito.doThrow(Exception.class).when(bookService).deleteBookByID(30L);
		ResultActions response = this.mockMvc.perform(get("/DeletingInformationPage/{id}", 30L)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		// then
		Mockito.verify(bookService).deleteBookByID(30L);
		Mockito.verify(bookService).findTitleById(30L);
		Mockito.verifyNoMoreInteractions(bookService);

		response.andExpect(status().isOk()).andExpect(view().name("NoBookWithSuchId"))
				.andExpect(model().attribute("booksCount", 1));
	}
}
