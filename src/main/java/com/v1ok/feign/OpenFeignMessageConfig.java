package com.v1ok.feign;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.v1ok.feign.gson.DateAdapter;
import com.v1ok.feign.gson.NumberAdapter;
import com.v1ok.feign.gson.StringAdapter;
import java.util.Date;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.v1ok.feign")
public class OpenFeignMessageConfig {


  @Bean
  @ConditionalOnMissingBean(Gson.class)
  public Gson gson() {
    return new GsonBuilder().registerTypeAdapter(Number.class, new NumberAdapter())
        .registerTypeAdapter(Date.class, new DateAdapter())
        .registerTypeAdapter(String.class, new StringAdapter())
        .create();

  }

}
