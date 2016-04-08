package com.vk.sales.product.web;

import com.vk.sales.product.web.utils.WebContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import javax.faces.webapp.FacesServlet;

@SpringBootApplication
@ImportResource(locations="classpath:/spring-context.xml")
public class BootWebApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(BootWebApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(BootWebApplication.class, WebContextInitializer.class);
  }

  @Bean
  public ServletRegistrationBean servletRegistrationBean() {
    FacesServlet servlet = new FacesServlet();
    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.jsf", "*.xhtml");
    return servletRegistrationBean;
  }
}
