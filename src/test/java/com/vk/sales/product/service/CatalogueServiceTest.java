package com.vk.sales.product.service;

import com.vk.sales.product.web.BaseTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class CatalogueServiceTest  extends BaseTestRunner {


  @Autowired
  private WebApplicationContext webApplicationContext;
 
  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testSetUp() throws Exception {
    mockMvc.perform(get("/getdefaultproducts").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"));
  }
  
  @Test
  public void test_getdefaultproducts() throws Exception {
    mockMvc.perform(get("/getdefaultproducts"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].category").value("News"))
        .andExpect(jsonPath("$[0].name").value("Sky News"))
        .andExpect(jsonPath("$[1].category").value("News"))
        .andExpect(jsonPath("$[1].name").value("Sky Sports News"));
    
  }
  
  @Test
  public void test_getLondonProducts() throws Exception {
    mockMvc.perform(get("/getproducts?locationId=LONDON"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].category").value("Sports"))
        .andExpect(jsonPath("$[0].name").value("Arsenal TV"))
        .andExpect(jsonPath("$[1].name").value("Chelsea TV"));
    
  }
  
  @Test
  public void test_getLiverpoolProducts() throws Exception {
    mockMvc.perform(get("/getproducts?locationId=LIVERPOOL"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].category").value("Sports"))
        .andExpect(jsonPath("$[0].name").value("Liverpool TV"));
    
  }
}
