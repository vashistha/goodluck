package com.vk.sales.product.service;

import com.vk.sales.product.service.exception.LocationNotFoundException;
import com.vk.sales.product.web.BaseTestRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CustomerLocationServiceTest  extends BaseTestRunner {

  private static final Logger logger = LoggerFactory.getLogger(CustomerLocationServiceTest.class);
  @Autowired
  private CustomerLocationService customerLocationService;
  
  @Rule
  public final ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testServiceInstance() {
    assertThat(customerLocationService, instanceOf(StubCustomerLocationService.class));
  }
  
  @Test
  public void testLondonLocation() {
    try {
      assertTrue("LONDON".equals(customerLocationService.getLocationId("LON5")));
      assertTrue(!"LONDON".equals(customerLocationService.getLocationId("5LON")));
    }
    catch (LocationNotFoundException e) {
      logger.error("",e);
    }
  }
  
  @Test
  public void testLiverpoolLocation() {
    try {
      assertTrue("LIVERPOOL".equals(customerLocationService.getLocationId("LIV5")));
      assertTrue(!"LIVERPOOL".equals(customerLocationService.getLocationId("Liv�")));
    }
    catch (LocationNotFoundException e) {
      logger.error("",e);
    }
  }
  
  @Test
  public void testFailureLocation() throws LocationNotFoundException {
    exception.expect(LocationNotFoundException.class);
    exception.expectMessage("There is a problem in retriving customer information");
    customerLocationService.getLocationId("LEE");
  }
}
