package com.jbdl5.nosql.bookapp.service;

import com.jbdl5.nosql.bookapp.controller.BookController;
import com.jbdl5.nosql.bookapp.dao.BookDao;
import com.jbdl5.nosql.bookapp.model.Book;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  private static Logger logger = LoggerFactory.getLogger(BookController.class);

  @Autowired
  BookDao bookDao;

  @Autowired
  ExecutorService executorService;

  @Override
  public List<Book> getBooksByAuthorName(String authorName) {
    //return bookDao.getBooksByAuthorName(authorName);
    return bookDao.getBooksByAuthorName(authorName);
  }

  @Override
  public void createBook(Book book) {

    bookDao.insert(book);

  }

  @Override
  public void bulkCreateBooks(List<Book> books) {
    long start = System.currentTimeMillis();
    bookDao.insert(books);
    long end = System.currentTimeMillis();
    logger.info("Total time using native insert iterable {}",end-start);
  }

  /*
  * Spring Mongo Repo's insert  collection of books is recommended comparing to mulithreaded approach below;
  * By OOTB , Spring framework- DB Repo implementation handles the calls to DB layer in an optimized way.
  * there is no explicit need to handle the DB layer calls via mulitple threads.
  *
  * */

  @Override
  public void bulkCreateConcurrently(List<Book> books) {

    long start = System.currentTimeMillis();
    int cnt = books.size();

    logger.info("saving books of size {}", cnt);
    logger.info("Current Thread {}", Thread.currentThread().getName());

    // spliting the list of books into two

    List<Book> lowHalf = books.subList(0,cnt/2);
    List<Book> highHalf = books.subList(cnt/2, cnt);

    List<List<Book>> bookToProcess = new ArrayList<>();
    bookToProcess.add(lowHalf);
    bookToProcess.add(highHalf);

    List<Book> processed = new ArrayList<>();

    try {
      processed =  bookToProcess.stream().map( l -> CompletableFuture.supplyAsync(
          new Supplier<List<Book>>() {
            @Override
            public List<Book> get() {
              logger.info("Inside async");
              logger.info("Thread {}",Thread.currentThread().getName());
               bookDao.insert(l);
              return l;
            }
          },executorService)).map(CompletableFuture::join).collect(Collectors.toList()).stream().flatMap(List::stream).collect(Collectors.toList());

    } catch (Exception e) {
      logger.info("Exception e {}",e.getMessage());
    }

    logger.info("Processed count {}",processed.size());

    

    if(processed.size() != cnt){
      logger.info("Delete All call. current count is {}",bookDao.findAll().size());
      bookDao.deleteAll(books);
    }

    long end = System.currentTimeMillis();
    logger.info("Total time using insert iterable {}",end-start);

  }



  @Override
  public Book getBookById(Long id) {
    return null;
  }

  @Override
  public List<Book> getBooks() {
    return bookDao.findAll();
  }
}
