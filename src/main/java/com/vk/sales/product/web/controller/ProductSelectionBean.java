package com.vk.sales.product.web.controller;

import com.vk.sales.product.model.Product;
import com.vk.sales.product.service.CustomerLocationService;
import com.vk.sales.product.service.exception.LocationNotFoundException;
import com.vk.sales.product.web.utils.SpringBeanInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SessionScoped
@ManagedBean(name="productBean")
public class ProductSelectionBean extends SpringBeanInitializer {
  private static final Logger logger = LoggerFactory.getLogger(ProductSelectionBean.class);
  
  private String customerId;
  private List<Product> defaultProducts;
  private List<Product> localProducts;
  private List<String> defaultSelected;
  private List<String> localSelected;
  
  @Autowired
  private CustomerLocationService customerLocationService;
  
  
  @PostConstruct
  public void init() {
    super.initContext();
    readCookies();
    
    String location = getCustomerLocation(customerId);
    populateAvailableProducts(location);
    defaultSelected = new ArrayList<>();
    localSelected = new ArrayList<>();
  }
  
  /**
   * Calls catalogue service and populates the default as well as local products   
   * @param location
   */
  private void populateAvailableProducts(String location) {
    defaultProducts = new ArrayList<>();
    localProducts = new ArrayList<>();
    try {
      HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      URI defaultProdUri = new URI(req.getScheme(), null, req.getServerName(), req.getServerPort(), req.getContextPath() + "/getdefaultproducts", null, null);
      URI localProdUri = new URI(req.getScheme(), null, req.getServerName(), req.getServerPort(), req.getContextPath() + "/getproducts", "locationId="+location, null);
      
      RestTemplate restTemplate = new RestTemplate();
      
      Product[] prodArray = restTemplate.getForObject(defaultProdUri, Product[].class);
      if(prodArray !=null) {
        defaultProducts.addAll(Arrays.asList(prodArray));
        logger.debug("******************Default products are :"+ defaultProducts);
      }
      
      Product[] localProdArray = restTemplate.getForObject(localProdUri, Product[].class);
      if(localProdArray !=null) {
        localProducts.addAll(Arrays.asList(localProdArray));
        logger.debug("****************** Products for location '"+location+"' are :"+ localProducts);
      }
    }
    catch (Exception e) {
      logger.error("Exception in retrieving product catalogue", e);
    }
  }

  /**
   * Call location service.
   * @param customerId
   * @return location
   */
  private String getCustomerLocation(String customerId) {
    String location = null;
    try {
      location = customerLocationService.getLocationId(customerId);
    }
    catch (LocationNotFoundException lnfe) {
      logger.error("Exception in retrieving location", lnfe);
    }
    return location;
  }

  /**
   * Reads cookies.
   */
  private void readCookies() {
    Map<String, Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
    Cookie cookie = (Cookie) cookies.get("customerID");
    if(cookie != null) {
      customerId = cookie.getValue();
      logger.debug("tastie cookies :", customerId);
    }
  }

  
  /*
   * These getters called by JSF EL on xhtml page.  
   */
  public List<Product> getDefaultProducts() {
    return defaultProducts;
  }

  public List<Product> getLocalProducts() {
    return localProducts;
  }
  
  public String getCustomerId() {
    return customerId;
  }

  public List<String> getDefaultSelected() {
    return defaultSelected;
  }

  public void setDefaultSelected(List<String> defaultSelected) {
    this.defaultSelected = defaultSelected;
  }

  public List<String> getLocalSelected() {
    return localSelected;
  }

  public void setLocalSelected(List<String> localSelected) {
    this.localSelected = localSelected;
  }
 }
