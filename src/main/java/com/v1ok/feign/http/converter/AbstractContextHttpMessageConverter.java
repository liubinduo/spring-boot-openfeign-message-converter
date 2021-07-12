package com.v1ok.feign.http.converter;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Util;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;

@Slf4j
public abstract class AbstractContextHttpMessageConverter<Context> extends
    AbstractJsonHttpMessageConverter {

  protected Class<Context> contextClass;

  protected final ObjectMapper objectMapper;

  public AbstractContextHttpMessageConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    Type genType = getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
    contextClass = (Class<Context>) ResolvableType.forType(params[0]).resolve();
  }

  @Override
  protected Object readInternal(Type type, Reader reader) throws Exception {
    Class<?> resolve = ResolvableType.forType(type).resolve();

    String content = Util.toString(reader);

    log.info("convert str content to type[{}]:{} ", resolve.getName(), content);

    return objectMapper.readValue(content, resolve);
  }

  @Override
  protected void writeInternal(Object object, Type type, Writer writer) {
  }

  @Override
  protected boolean canWrite(MediaType mediaType) {
    return false;
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
