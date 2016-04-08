package com.vk.sales.product.service;

import com.vk.sales.product.service.exception.LocationNotFoundException;
import org.springframework.stereotype.Service;

@Service("customerLocationService")
public class StubCustomerLocationService implements CustomerLocationService {

  /**
   * Dummy implementation of getLocationId operation.
   */
  public String getLocationId(String customerId) throws LocationNotFoundException {
    String location = "";
    if (customerId == null) {
      return location;
    }
    else if(customerId.startsWith("LON")) {
      location = "LONDON";
    }
    else if (customerId.startsWith("LIV")) {
      location = "LIVERPOOL";
    }
    else if (customerId.startsWith("LEE")) {
      throw new LocationNotFoundException("There is a problem in retriving customer information");
    }
    return location;
  }

}
