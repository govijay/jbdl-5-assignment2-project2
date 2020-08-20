package com.jbdl5.nosql.bookapp.controller;

import com.jbdl5.nosql.bookapp.constants.Constants;
import com.jbdl5.nosql.bookapp.dto.BookDto;
import com.jbdl5.nosql.bookapp.model.Book;
import com.jbdl5.nosql.bookapp.service.BookServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = Constants.ROOT_URL,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public class BookController {


  private static Logger logger = LoggerFactory.getLogger(BookController.class);

  @Autowired
  BookServiceImpl bookService;


  @GetMapping(path = Constants.BOOKS_URL)
  public ResponseEntity<List<BookDto>> getBooks() {
    List<BookDto> books =
        bookService.getBooks().stream()
            .map(book -> BookDto.toDto(book))
            .collect(Collectors.toList());
    return new ResponseEntity<>(books, HttpStatus.OK);
  }


  @GetMapping(path = Constants.BOOKS_AUTH_URL)
  public ResponseEntity<List<BookDto>> getBooksByAuthor(
      @PathVariable(name = "author", required = true) @NotBlank String author) {
    List<BookDto> books =
        bookService.getBooksByAuthorName(author).stream()
            .map(book -> BookDto.toDto(book))
            .collect(Collectors.toList());
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @PostMapping(path = Constants.BOOK_URL)
  public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto) {
    bookService.createBook(Book.toEntity(bookDto));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(path = Constants.BULK_BOOKS_URL)
  public ResponseEntity<BookDto> createBulkBooks(@Valid @RequestBody List<BookDto> bookDtoList) {
    List<Book> books = new ArrayList<>(bookDtoList.stream().map(bookDto -> (Book.toEntity(bookDto))).collect(
        Collectors.toList()));
    bookService.bulkCreateBooks(books);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(path = Constants.BOOKS_URL)
  public ResponseEntity<BookDto> createBulkBooksInParallel(@Valid @RequestBody List<BookDto> bookDtoList) {
    List<Book> books = new ArrayList<>(bookDtoList.stream().map(bookDto -> (Book.toEntity(bookDto))).collect(
        Collectors.toList()));
    bookService.bulkCreateConcurrently(books);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
