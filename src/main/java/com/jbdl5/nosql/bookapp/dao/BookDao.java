package com.jbdl5.nosql.bookapp.dao;

import com.jbdl5.nosql.bookapp.model.Book;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao extends MongoRepository<Book,Integer> {

 List<Book> getBooksByAuthorName(String authorName);

}
