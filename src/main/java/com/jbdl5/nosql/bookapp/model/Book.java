package com.jbdl5.nosql.bookapp.model;

import com.jbdl5.nosql.bookapp.dto.BookDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Book {

  @Id
  Long bookId;

  String name;

  String authorName;

  Integer cost;

  public Book() {}

  public Book(Long bookId, String name, String authorName, Integer cost) {
    this.bookId = bookId;
    this.name = name;
    this.authorName = authorName;
    this.cost = cost;
  }

  public Long getBookId() {
    return bookId;
  }

  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public Integer getCost() {
    return cost;
  }

  public void setCost(Integer cost) {
    this.cost = cost;
  }

  public static Book toEntity(BookDto bookDto) {
    return new Book(bookDto.getId(), bookDto.getName(), bookDto.getAuthorName(), bookDto.getCost());
  }

  @Override
  public String toString() {
    return "Book{" +
        "bookId=" + bookId +
        ", name='" + name + '\'' +
        ", authorName='" + authorName + '\'' +
        ", cost=" + cost +
        '}';
  }
}


