package com.v1ok.feign.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class NumberAdapter implements JsonSerializer<Number>, JsonDeserializer<Number> {

  /**
   * Gson invokes this call-back method during deserialization when it encounters a field of the
   * specified type.
   * <p>In the implementation of this call-back method, you should consider invoking
   * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects for
   * any non-trivial field of the returned object. However, you should never invoke it on the the
   * same type passing {@code json} since that will cause an infinite loop (Gson will call your
   * call-back method again).
   *
   * @param json The Json data being deserialized
   * @param typeOfT The type of the Object to deserialize to
   * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
   * @throws JsonParseException if json is not in the expected format of {@code typeofT}
   */
  @Override
  public Number deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {

    String string = json.getAsString();
    if (StringUtils.isBlank(string)) {
      return null;
    }

    if (NumberUtils.isCreatable(string)) {
      return json.getAsNumber();
    }

    return convert(string);
  }

  /**
   * Gson invokes this call-back method during serialization when it encounters a field of the
   * specified type.
   *
   * <p>In the implementation of this call-back method, you should consider invoking
   * {@link JsonSerializationContext#serialize(Object, Type)} method to create JsonElements for any
   * non-trivial field of the {@code src} object. However, you should never invoke it on the {@code
   * src} object itself since that will cause an infinite loop (Gson will call your call-back method
   * again).</p>
   *
   * @param src the object that needs to be converted to Json.
   * @param typeOfSrc the actual type (fully genericized version) of the source object.
   * @return a JsonElement corresponding to the specified object.
   */
  @Override
  public JsonElement serialize(Number src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src);
  }


  private Number convert(String str) {
    String notNumberStr = RegExUtils.removeAll(str, "[^[\\d]+[.][\\d]+]");
    String numberStr = StringUtils.replace(str, notNumberStr, "");

    if (StringUtils.isBlank(numberStr)) {
      return null;
    }

    return NumberUtils.createNumber(numberStr);
  }
}
