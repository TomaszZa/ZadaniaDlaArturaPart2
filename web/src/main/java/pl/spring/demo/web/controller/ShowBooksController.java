package pl.spring.demo.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

@Controller
public class ShowBooksController {
	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/ShowBooks", method = RequestMethod.GET)
	public String tableFromBooks(Map<String, Object> params) {
		final List<BookTo> allBooks = bookService.findAllBooks(); // 1 dlaczego
																	// final ?
																	// po co ?
		params.put("books", allBooks);
		return "bookListTable";
	}

	@RequestMapping(value = "/redirect/{id}", method = RequestMethod.GET)
	public String redirect(@PathVariable("id") Long id) {

		return "redirect:../DeletingInformationPage/" + id;
	}

	@RequestMapping(value = "/DeletingInformationPage/{id}", method = RequestMethod.GET)
	public String finalPage(Map<String, Object> params, @PathVariable("id") Long id) {
		params.put("title", bookService.findTitleById(id));
		params.put("booksCount", 1);
		params.put("id", id);

		try {
			bookService.deleteBookByID(id);
		} catch (Exception exc) {
			return "NoBookWithSuchId";
		}
		return "DeletingInformationPage";
	}
}
