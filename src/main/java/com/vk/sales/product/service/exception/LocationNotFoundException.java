package com.vk.sales.product.service.exception;

public class LocationNotFoundException extends Exception {

  private static final long serialVersionUID = -153531860416923705L;

  public LocationNotFoundException(String message) {
    super(message);
  }

  public LocationNotFoundException(String message, Throwable cause) {
    super(message, cause);
    
  }
}
