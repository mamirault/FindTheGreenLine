package com.mamirault.findthegreenline.resources;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.mamirault.findthegreenline.core.Train;
import com.yammer.metrics.annotation.Timed;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {
  private final String template;
  private final String defaultName;
  private final AtomicLong counter;

  public TestResource(String template, String defaultName) {
    this.template = template;
    this.defaultName = defaultName;
    this.counter = new AtomicLong();
  }

  @GET
  @Timed
  public Train sayHello(@QueryParam("name") Optional<String> name) {
    return new Train(counter.incrementAndGet(), String.format(template, name.or(defaultName)));
  }
}