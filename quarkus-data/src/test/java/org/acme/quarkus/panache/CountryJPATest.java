package org.acme.quarkus.panache;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Transactional
public class CountryJPATest {

  // @Inject 
  // @DataSource("pg")
  // AgroalDataSource pgDS;

  @Inject
  EntityManager em;

  @Test
  void storeCountry(){
    Country country = new Country();
    country.setCountry("China");
    em.persist(country);
  }

}