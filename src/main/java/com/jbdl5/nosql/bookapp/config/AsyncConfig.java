package com.jbdl5.nosql.bookapp.config;


import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

@Configuration
@Async
public class AsyncConfig {

  @Value("${threadpool.max.size:10}")
  private int threadPoolMaxSize;

  @Bean
  public ExecutorService executorService() {
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolMaxSize);
    return executor;
  }

}
