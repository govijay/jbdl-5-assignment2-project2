package com.jbdl5.nosql.bookapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jbdl5.nosql.bookapp.model.Book;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto {

  private Long id;

  private String name;

  private String authorName;

  private Integer cost;

  public BookDto() {
  }

  public BookDto(Long id, String name, String authorName, Integer cost) {
    this.id = id;
    this.name = name;
    this.authorName = authorName;
    this.cost = cost;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public static BookDto toDto(Book book) {
    BookDto bookDto =
        new BookDto(book.getBookId(), book.getName(), book.getAuthorName(), book.getCost());

    return bookDto;
  }

  @Override
  public String toString() {
    return "BookDto{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", authorName='" + authorName + '\'' +
        ", cost=" + cost +
        '}';
  }
}
