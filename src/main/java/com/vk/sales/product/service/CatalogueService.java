package com.vk.sales.product.service;

import com.vk.sales.product.model.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CatalogueService {

  /**
   * Returns default products for all location.
   * @return list of products.
   */
  @RequestMapping("/getdefaultproducts")
  public List<Product> getproducts() {
    List<Product> products = new ArrayList<Product>();

    /*
     *These values can be easily injected by Spring. For now leave these as hardcoded.
     */
    products.add(new Product("News", "Sky News"));
    products.add(new Product("News", "Sky Sports News"));
    return products;
   }
  
  /**
   * Returns location specific products.
   * @return list of products.
   */
  @RequestMapping("/getproducts")
  public List<Product> getproducts(@RequestParam(value="locationId") String locationId) {
    List<Product> products = new ArrayList<Product>();

    /*
     *These values can be easily injected by Spring either from XML config file or from database. 
     *For now leave these as hardcoded value.
     */
    if(locationId != null && locationId.equalsIgnoreCase("LONDON")) {
      products.add(new Product("Sports", "Arsenal TV"));
      products.add(new Product("Sports", "Chelsea TV"));
    }
    else if(locationId != null && locationId.equalsIgnoreCase("LIVERPOOL")) {
      products.add(new Product("Sports", "Liverpool TV"));
    }
    return products;
   }
}
