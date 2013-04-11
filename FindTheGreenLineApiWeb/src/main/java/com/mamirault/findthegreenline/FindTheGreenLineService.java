package com.mamirault.findthegreenline;

import java.util.concurrent.TimeUnit;

import com.mamirault.findthegreenline.configuration.FindTheGreenLineConfiguration;
import com.mamirault.findthegreenline.resources.StopResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class FindTheGreenLineService extends Service<FindTheGreenLineConfiguration> {
  public static void main(String[] args) throws Exception {
    new FindTheGreenLineService().run(args);
  }

  @Override
  public void initialize(Bootstrap<FindTheGreenLineConfiguration> bootstrap) {
    bootstrap.setName("find-the-green-line");
  }

  @Override
  public void run(FindTheGreenLineConfiguration configuration, Environment environment) {
    final String template = configuration.getTemplate();
    final String defaultName = configuration.getDefaultName();

    environment.addResource(new StopResource(template, defaultName));
  }
  
  private void initialize(){
    long currentTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
  }
}