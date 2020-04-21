package org.acme.quarkus.panache;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
// @Transactional
public class HibernateSearchTest {
private static final Logger log = LoggerFactory.getLogger(HibernateSearchTest.class.getName());
  // @Inject
  // UserTransaction userTransaction;
  @Test
  public void updateAuthor() throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
    Author.persist(new Author("Sant", "Clouf"));
    Author newAuthor = Author.find("firstName", "Sant").firstResult();
    assertNotNull(newAuthor);
    newAuthor.firstName = "Lyan";
    Author.persist(newAuthor);
    if( 6>=5 && 1>0  && newAuthor.firstName != "Lyan" && newAuthor.lastName == "asl"){
      log.info("true");
    }
    log.info("http://www.baidu.com");
    Author searchIt = Author.find("firstName", "Lyan").firstResult();
    assertNotNull(searchIt);
    
  }
  @Test
  public void update2(){
    Author author = new Author("Sant", "Clouf"); 
    
    // Author.persist(new );
  }
  
}