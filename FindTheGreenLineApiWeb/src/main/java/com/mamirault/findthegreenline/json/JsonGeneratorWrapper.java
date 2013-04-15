package com.mamirault.findthegreenline.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

import com.google.common.base.Preconditions;

public class JsonGeneratorWrapper {

  private final JsonGenerator jsonGenerator;
  private final ByteArrayOutputStream baos;
  private boolean closed = false;

  public JsonGeneratorWrapper() {
    baos = new ByteArrayOutputStream();
    jsonGenerator = initGenerator(baos);
  }

  public JsonGeneratorWrapper(OutputStream stream) {
    baos = null;
    jsonGenerator = initGenerator(stream);
  }

  private JsonGenerator initGenerator(OutputStream stream) {
    try {
      return JsonUtils.JSON_FACTORY.createJsonGenerator(stream, JsonEncoding.UTF8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public JsonGenerator getGenerator() {
    return jsonGenerator;
  }

  public JsonGeneratorWrapper startObject() {
    try {
      jsonGenerator.writeStartObject();
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper startArray() {
    try {
      jsonGenerator.writeStartArray();
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper startObject(String name) {
    try {
      jsonGenerator.writeObjectFieldStart(name);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper startArray(String name) {
    try {
      jsonGenerator.writeArrayFieldStart(name);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper endArray() {
    try {
      jsonGenerator.writeEndArray();
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper endObject() {
    try {
      jsonGenerator.writeEndObject();
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper number(int value) {
    try {
      jsonGenerator.writeNumber(value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper number(long value) {
    try {
      jsonGenerator.writeNumber(value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper number(float value) {
    try {
      jsonGenerator.writeNumber(value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper number(double value) {
    try {
      jsonGenerator.writeNumber(value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper bool(boolean value) {
    try {
      jsonGenerator.writeBoolean(value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper number(String fieldName, int value) {
    try {
      jsonGenerator.writeNumberField(fieldName, value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper number(String fieldName, long value) {
    try {
      jsonGenerator.writeNumberField(fieldName, value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper number(String fieldName, float value) {
    try {
      jsonGenerator.writeNumberField(fieldName, value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper number(String fieldName, double value) {
    try {
      jsonGenerator.writeNumberField(fieldName, value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }
  
  public JsonGeneratorWrapper number(String fieldName, BigDecimal value) {
    try {
      jsonGenerator.writeNumberField(fieldName, value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }
  
  public JsonGeneratorWrapper number(String fieldName, BigInteger value) {
    try {
      // strangely, no writeNumberField exists for this
      jsonGenerator.writeFieldName(fieldName);
      jsonGenerator.writeNumber(value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper bool(String fieldName, boolean value) {
    try {
      jsonGenerator.writeBooleanField(fieldName, value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper string(String value) {
    try {
      jsonGenerator.writeString(value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper string(String fieldName, String value) {
    try {
      jsonGenerator.writeStringField(fieldName, value);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper field(String fieldName) {
    try {
      jsonGenerator.writeFieldName(fieldName);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public JsonGeneratorWrapper nullValue(String fieldName) {
    try {
      jsonGenerator.writeNullField(fieldName);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  public String asString() {
    try {
      return new String(asBytes(), HTTP.UTF_8);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public void close() {
    Preconditions.checkState(!closed, "wrapper is already closed");
    closed = true;
    try {
      jsonGenerator.flush();
      jsonGenerator.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public byte[] asBytes() {
    Preconditions.checkState(baos != null, "wrapper is using an external output stream");
    close();
    return baos.toByteArray();
  }

}