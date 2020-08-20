package com.jbdl5.nosql.bookapp.service;

import com.jbdl5.nosql.bookapp.model.Book;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BookService {

  List<Book> getBooksByAuthorName(String authorName);

  void createBook(Book book);

  void bulkCreateBooks(List<Book> books);

  void bulkCreateConcurrently(List<Book> books);

  Book getBookById(Long id);

  List<Book> getBooks();

}
