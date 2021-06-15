package com.v1ok.feign.gson;

import com.google.gson.Gson;
import com.v1ok.feign.AbstractGsonHttpMessageConverter;
import com.v1ok.feign.Response;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ResponseGsonHttpMessageConverter extends
    AbstractGsonHttpMessageConverter<Response<?>> {

  public ResponseGsonHttpMessageConverter(@NonNull Gson gson) {
    super(gson);
  }
}
