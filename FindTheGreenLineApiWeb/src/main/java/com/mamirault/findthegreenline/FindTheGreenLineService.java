package com.mamirault.findthegreenline;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.mamirault.findthegreenline.configuration.FindTheGreenLineConfiguration;
import com.mamirault.findthegreenline.resources.CheckInResource;
import com.mamirault.findthegreenline.resources.StationResource;
import com.mamirault.findthegreenline.resources.StopResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.FilterBuilder;

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
    
    FilterBuilder filterBuilder = environment.addFilter(CrossOriginFilter.class, "/*");
    filterBuilder.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "http://*");
    //filterBuilder.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "http://ec2-50-19-184-2.compute-1.amazonaws.com:3333");
    //filterBuilder.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "ec2-50-19-184-2.compute-1.amazonaws.com:3333");
    //filterBuilder.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "http://findthegreenline.com");

    environment.addResource(new StopResource(template, defaultName));
    environment.addResource(new StationResource(template, defaultName));
    environment.addResource(new CheckInResource());
  }
}