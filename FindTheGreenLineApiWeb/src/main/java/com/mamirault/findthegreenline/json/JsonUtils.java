package com.mamirault.findthegreenline.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {


  public final static JsonFactory JSON_FACTORY = new JsonFactory();
  
  static {
    JSON_FACTORY.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    JSON_FACTORY.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, true);
  }
  
  public static String listToJson(List<Object> list) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectMapper om = new ObjectMapper(JSON_FACTORY);
    try {
      om.writeValue(baos, list);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    return baos.toString();
  }
  
  public static String mapToJson(Map<String, Object> map) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectMapper om = new ObjectMapper(JSON_FACTORY);
    try {
      om.writeValue(baos, map);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return baos.toString();
  }

  @SuppressWarnings("unchecked")
  public static Map<String, Object> mapFromString(String json) {
    try {
      return new ObjectMapper(JSON_FACTORY).readValue(json, Map.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static JsonNode fromString(String string) {
    try {
      return new ObjectMapper(JSON_FACTORY).readValue(string, JsonNode.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static JsonNode fromInputStream(InputStream inputStream) {
    try {
      return new ObjectMapper(JSON_FACTORY).readValue(inputStream, JsonNode.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<String> stringsFromArray(JsonNode array) {
    List<String> list = new ArrayList<String>(array.size());
    for (int i = 0; i < array.size(); i++) {
      list.add(array.get(i).getTextValue());
    }
    return list;
  }
  
  public static List<Long> longsFromArray(JsonNode array) {
    List<Long> list = new ArrayList<Long>(array.size());
    for (int i = 0; i < array.size(); i++) {
      list.add(array.get(i).getLongValue());
    }
    return list;
  }
  
}
