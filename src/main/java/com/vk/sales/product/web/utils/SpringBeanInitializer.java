package com.vk.sales.product.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
/**
 * **** USE (INHERIT) THIS CLASS, ONLY IF SPRING BEAN INJECTION or @Autowired IS REQUIRED IN JSF MANAGED BEAN ****  <br> 
 * --------------------------------------------------------------------------------------------------------------------------- <br>
 * 
 * http://www.beyondjava.net/blog/integrate-jsf-2-spring-3-nicely/
 * http://codereview.stackexchange.com/questions/23790/spring-autowiring-in-managed-beans-with-support-for-serialization-is-this-safe
 * Problems: 
 * 1. @Autowired in @ManagedBean does not work.
 * 2. Nor does @ViewScoped with @Controller.
 * 3. @ManagedProperty is not type safe and requires setter.
 * 4. You can not serialize singleton spring beans without hassle.
 * 5. Managed beans must be serializable so the container can serialize the session.
 * 
 */
public class SpringBeanInitializer implements Serializable {

  private static final Logger logger = LoggerFactory.getLogger(SpringBeanInitializer.class);
  /**
   * Initialise the beans(Spring) with new application context when JSF bean get de-serialesed in session.  
   */

  public void initContext() {
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    ServletContext servletContext = (ServletContext) externalContext.getContext();
    WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                                 getAutowireCapableBeanFactory().
                                 autowireBean(this);
    logger.debug("Bean intialised with new context.");
  }
  
  /**
   * Used to de-serialises the JSF bean and make sure autowired beans(spring) injected with new application context.
   * @param ois
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
    initContext();
  }
}
