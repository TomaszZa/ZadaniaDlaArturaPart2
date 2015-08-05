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
		final List<BookTo> allBooks = bookService.findAllBooks();
		params.put("books", allBooks);
		return "bookListTable";
	}

	@RequestMapping(value = "/redirect/{id}", method = RequestMethod.GET)
	public String redirect(@PathVariable("id") Long id) {
		System.out.println(id);
		return "redirect:../DeletingInformationPage";
	}

	@RequestMapping(value = "DeletingInformationPage", method = RequestMethod.GET)
	public String finalPage(Map<String, Object> params) {
		// , @PathVariable("book") BookTo book
		// bookService.deleteBookByID(id);
		// params.put("title", book);
		return "DeletingInformationPage";
	}
}
