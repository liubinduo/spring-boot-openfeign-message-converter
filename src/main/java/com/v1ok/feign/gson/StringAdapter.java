package com.v1ok.feign.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.apache.commons.lang3.StringUtils;

public class StringAdapter implements JsonSerializer<String>, JsonDeserializer<String> {

  @Override
  public String deserialize(JsonElement jsonElement, Type type,
      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

    if (StringUtils.isBlank(jsonElement.getAsString())) {
      return null;
    }
    return jsonElement.getAsString();
  }

  @Override
  public JsonElement serialize(String s, Type type,
      JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(s);
  }
}
