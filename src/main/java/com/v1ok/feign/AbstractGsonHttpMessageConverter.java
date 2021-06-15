package com.v1ok.feign;

import com.google.gson.Gson;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import lombok.NonNull;
import org.apache.commons.lang3.Validate;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;


public abstract class AbstractGsonHttpMessageConverter<Context> extends GsonHttpMessageConverter {

  protected Class<Context> contextClass;


  public AbstractGsonHttpMessageConverter(@NonNull Gson gson) {

    super(gson);

    Type genType = getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
    contextClass = (Class<Context>) ResolvableType.forType(params[0]).resolve();
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    return clazz.isAssignableFrom(contextClass);
  }

  @Override
  public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {

    Validate.notNull(type, "The Type is not null");

    if (contextClass != null) {
      return this.supports(contextClass);
    }

    Class<?> resolve = ResolvableType.forType(type).resolve();

    return this.supports(resolve);

  }

  @Override
  public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
    return false;
  }
}
