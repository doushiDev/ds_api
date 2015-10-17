package me.doushi.api.config;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("resources")
public class RestJaxRsApplication extends ResourceConfig {
  public RestJaxRsApplication() {
    String myRestPackage = "me.doushi.api.resource";
    String jacksonPackage = "org.codehaus.jackson.jaxrs";
    String swaggerJaxrsJsonPackage = "io.swagger.jaxrs.json";
    String swaggerJaxrsListingPackage = "io.swagger.jaxrs.listing";
    packages(new String[]{swaggerJaxrsJsonPackage, swaggerJaxrsListingPackage, jacksonPackage,myRestPackage});
    register(MultiPartFeature.class);
  }
}