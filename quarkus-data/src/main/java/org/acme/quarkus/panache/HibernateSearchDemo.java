package org.acme.quarkus.panache;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.search.backend.elasticsearch.analysis.model.dsl.ElasticsearchAnalysisComponentParametersStep;
import org.hibernate.search.backend.elasticsearch.analysis.model.dsl.ElasticsearchAnalysisComponentTypeStep;
import org.hibernate.search.backend.elasticsearch.analysis.model.dsl.ElasticsearchAnalyzerTypeStep;
import org.hibernate.search.backend.elasticsearch.analysis.model.dsl.ElasticsearchNormalizerTypeStep;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.StartupEvent;

@Path("library")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Transactional
@ApplicationScoped
public class HibernateSearchDemo {
  private static final Logger log = LoggerFactory.getLogger(HibernateSearchDemo.class.getName());
  @Inject
  EntityManager em;

  void onStart(@Observes StartupEvent ev) throws InterruptedException{
    if(Book.count() > 0){
      Search.session(em)
        .massIndexer()
        .startAndWait();
    }
  }

  @GET
  @Path("author/search")
  public List<Author> searchAuthors(@QueryParam String pattern, 
    @QueryParam Optional<Integer> size){
      return Search.session(em)
        .search(Author.class)
        .where(f -> pattern==null || pattern.isEmpty() ? f.matchAll():
           f.simpleQueryString().fields("firstName", "lastName", "books.title")
            .matching(pattern)
        )
        .sort(f -> f.field("lastName_sort").then().field("firstName_sort"))
        .fetchHits(size.orElse(20));
  }
  @PUT
  @Path("/book")
  public void addBook(@FormParam String title, @FormParam Long authorId){
    Author author = Author.findById(authorId);
    
    if(author == null){
      return;
    }
    Book book = new Book(title, author);
    book.persist();
    author.books.add(book);
    author.persist();
  }

  @DELETE
  @Path("/book/{id}")
  public void deleteBook(@PathParam Long id){
    Book book =Book.findById(id);
    if(book != null){
      book.author.books.remove(book);
      book.delete();
    }
  }

  @POST
  @Path("/author")
  public void addAuthor(@FormParam String firstName, @FormParam String lastName){
    Author author = new Author(firstName, lastName);
    author.persist();
  }

  @PUT
  @Path("/author/{id}")
  @Transactional
  public void updateAuthor(@PathParam Long id, @FormParam String firstName, 
    @FormParam String lastName){
      Author author = Author.findById(id);
      log.warn(" " + id + " " + author.toString() + " " + firstName + " " + lastName);
      if(author == null){
        return;
      }
      author.setFirstName(firstName);;
      author.setLastName(lastName);
      author.persist();
  }

  @DELETE
  @Path("/author/{id}")
  public void deleteAuthor(@PathParam Long id){
    Author author = Author.findById(id);
    if(author != null){
        author.delete();
    }
  }

}
@Entity @Indexed
class Book extends PanacheEntity{
  @FullTextField(analyzer = "english")
  public String title;
  
  @ManyToOne
  @JsonBackReference("book-author")
  Author author;

/**
 * @param title
 * @param author
 */
public Book(String title, Author author) {
	this.title = title;
	this.author = author;
}

/**
 * 
 */
public Book() {
}

}

@Entity @Indexed
class Author extends PanacheEntity {
  @FullTextField(analyzer = "name")
  @KeywordField(name = "firstName_sort", sortable = Sortable.YES, normalizer = "sort")
  String firstName;

  @FullTextField(analyzer = "name")
  @KeywordField(name = "lastName_sort", sortable = Sortable.YES, normalizer = "sort")
  String lastName;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true,
    fetch = FetchType.EAGER)
  @JsonManagedReference("book-author")
  @IndexedEmbedded
  public List<Book> books;

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the books
   */
  public List<Book> getBooks() {
    return books;
  }

  /**
   * @param books the books to set
   */
  public void setBooks(List<Book> books) {
    this.books = books;
  }

/**
 * @param firstName
 * @param lastName
 */
public Author(String firstName, String lastName) {
	this.firstName = firstName;
	this.lastName = lastName;
}

/**
 * 
 */
public Author() {
}

@Override
public boolean equals(Object o) {
    if (this == o) {
        return true;
    }
    if (!(o instanceof Book)) {
        return false;
    }

    Book other = (Book) o;

    return Objects.equals(id, other.id);
}

@Override
public int hashCode() {
    return 31;
}  
}