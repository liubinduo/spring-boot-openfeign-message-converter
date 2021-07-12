package com.v1ok.feign.http.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.v1ok.feign.Response;
import org.springframework.stereotype.Component;

@Component
public class JacksonHttpMessageConverter extends AbstractContextHttpMessageConverter<Response<?>> {

  public JacksonHttpMessageConverter(ObjectMapper objectMapper) {
    super(objectMapper);
  }
}
