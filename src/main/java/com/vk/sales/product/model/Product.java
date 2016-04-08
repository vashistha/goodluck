package com.vk.sales.product.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
  private String category;
  private String name;
  
  public Product() {
    super();
  }

  public Product(String category, String name) {
    super();
    this.category = category;
    this.name = name;
  }

  public String getCategory() {
    return category;
  }
  
  public void setCategory(String category) {
    this.category = category;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "<Product><category>" + category + "</category><name>" + name + "</name></Product>";
  }
}
