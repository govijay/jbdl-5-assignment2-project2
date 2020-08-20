package com.jbdl5.nosql.bookapp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookappApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookappApplication.class, args);
  }


}
