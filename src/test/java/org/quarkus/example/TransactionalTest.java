package org.quarkus.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.inject.Stereotype;
import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

/**
 * TransactionalTest
 */
// @QuarkusTest
// @Transactional
@TransactionalQuarkusTest
public class TransactionalTest {

  @Inject
  UserTransaction userTransaction;
  
  @Test
  void testUserTransaction() throws Exception{
    assertNotNull(userTransaction);
    assertEquals(Status.STATUS_ACTIVE, userTransaction.getStatus());
  }
}

@QuarkusTest
@Stereotype
@Transactional
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface TransactionalQuarkusTest{
}