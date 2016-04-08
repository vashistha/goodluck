package com.vk.sales.product.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@ViewScoped
@ManagedBean(name="welcomeBean")
public class WelcomeBean implements Serializable {
  private static final Logger logger = LoggerFactory.getLogger(WelcomeBean.class);
  
  private String customerId;
  
  
  /**
   * Sets cookies for next pages and navigates the page to product_selection.jsf
   * @return product_selection.jsf url.
   * @throws UnsupportedEncodingException 
   */
  public String toProductSelection() {
    
    Map<String, Object> cookiesProp = new HashMap<>();
    cookiesProp.put("maxAge", 31536000);
    cookiesProp.put("path", "/");
    try {
      FacesContext.getCurrentInstance().getExternalContext()
                  .addResponseCookie("customerID", URLEncoder.encode(customerId, "UTF-8"), cookiesProp);
    }
    catch (UnsupportedEncodingException e) {
      logger.error("Exception thrown in setting cookies value", e);
    }
    return "/product_selection.xhtml?faces-redirect=true";
  }
  
  public String getCustomerId() {
    Map<String, Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
    Cookie cookie = (Cookie) cookies.get("customerID");
    if(cookie != null) {
      customerId = cookie.getValue();
      logger.debug("tastie cookies :", customerId);
    }
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
    logger.debug("Set is called."+customerId);
  }

}
