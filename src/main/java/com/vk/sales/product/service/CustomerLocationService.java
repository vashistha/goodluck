package com.vk.sales.product.service;

import com.vk.sales.product.service.exception.LocationNotFoundException;

public interface CustomerLocationService {
  public String getLocationId(String customerId) throws LocationNotFoundException;
}
