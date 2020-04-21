package org.acme.quarkus.panache;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class GiftJPATest {
  @Inject
  EntityManager em;

  @Test
  @Transactional
  void createGift(){
    Gift gift = new Gift();
    gift.setName("Father's gift");
    em.persist(gift);
    
  }

}