package com.msa.delivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class FeignConfiguration {

  @Bean
  public OkHttpClient client() {
    return new OkHttpClient();
  }
}
