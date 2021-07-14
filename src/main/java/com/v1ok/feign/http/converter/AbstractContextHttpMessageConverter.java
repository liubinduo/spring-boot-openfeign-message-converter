package com.v1ok.feign.http.converter;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Slf4j
public abstract class AbstractContextHttpMessageConverter<Context> extends
    MappingJackson2HttpMessageConverter {

  protected Class<Context> contextClass;

  protected final ObjectMapper objectMapper;

  public AbstractContextHttpMessageConverter(ObjectMapper objectMapper) {
    super(objectMapper);
    this.objectMapper = objectMapper;
    this.objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
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

}
