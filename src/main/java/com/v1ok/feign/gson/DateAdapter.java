package com.v1ok.feign.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

public class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

  private final static String[] PATTERN = new String[]{"yyyy-MM-dd hh:mm:ss",
      "yyyy-MM-dd'T'HH:mm:ssXX"};

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
  @SneakyThrows
  @Override
  public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {

    String value = json.getAsString();
    if (StringUtils.isBlank(value)) {
      return null;
    }

    if (NumberUtils.isCreatable(value)) {
      return new Date(NumberUtils.createLong(value));
    }

    return parse(value);
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
  public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {

    return new JsonPrimitive(FastDateFormat.getInstance(PATTERN[1]).format(src));
  }


  private Date parse(String dateStr) {

    for (String parStr : PATTERN) {

      try {
        FastDateFormat fastDateFormat = FastDateFormat.getInstance(parStr);
        return fastDateFormat.parse(dateStr);

      } catch (ParseException ignored) {
      }
    }

    try {
      return DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.parse(dateStr);
    } catch (ParseException e) {
    }

    return null;
  }

  public static void main(String[] args) throws ParseException {

//    String format = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(new Date());
//
//    System.out.println(format);
//
    String source = "2020-08-17T22:19:11+0800";
//    Date d = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.parse(source);
    String format1 = FastDateFormat.getInstance("").format(new Date());
    System.out.println(format1);
//    Date parse = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT
//        .parse(source);
//    System.out.println(parse);
  }

}
