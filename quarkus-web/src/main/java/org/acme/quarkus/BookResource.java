package org.acme.quarkus;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
  @Inject
  Validator validator;

  @Inject
  BookService service;

  Set<Book> allBook = Collections.newSetFromMap(Collections.synchronizedMap(
    new LinkedHashMap<>()
  ));
  // Set<Book> allBook = new HashSet<>();
  public BookResource(){
    allBook.add(new Book("7 Great habits", "John", 500));
    allBook.add(new Book("Sun's tricks", "Great Sun", 300));
  }

  @GET
  public Set<Book> getBooks(){
    return allBook;
  }

  @GET
  @Path("{author}")
  public Optional<Book> getBook(@PathParam("author") String authorName){
    return allBook.stream().filter(e-> e.author.equalsIgnoreCase(authorName))
      .findFirst();
  }

  @POST
  @Path("/manual-validation")
  public Result tryManualValidation(Book book){
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    if(violations.isEmpty()){
      allBook.add(book);
      return new Result("Book is valid! It waas validated by manual validation", true);
    }else{
      return new Result(violations);
    }
  }
  @POST
  @Path("/auto-validation")
  public Result tryAutoValidation(@Valid Book book){
    allBook.add(book);
    return new Result("Book is valid! It waas validated by manual validation", true);
  }
  @POST
  @Path("/service-validation")
  public Result tryServiceAutoValidation(Book book){
    try {
      service.validateBook(book);
      allBook.add(book);  
      return new Result("Book is valid! It waas validated by manual validation", true);
    } catch (ConstraintViolationException e) {
      return new Result(e.getConstraintViolations());
    }
  }
  @DELETE
  @Path("{author}")
  public Result deleteByAuthor(@PathParam("author") String authorName){
    boolean foundandremove = allBook.removeIf(e->e.author.equalsIgnoreCase(authorName));
    //   item -> allBook.remove(item));
    return !foundandremove ? new Result("Not delete any book since no found", false): 
    new Result("Successfully deleted, current books size: " + allBook.size(), true);
  }
  
  public static class Result{
    
    public String message;
    public boolean success;
    Result(Set<? extends ConstraintViolation<?>> violations){
      this.success = false;
      this.message = violations.stream()
        .map(cv->cv.getMessage())
        .collect(Collectors.joining(", "));
    }
	/**
	 * @param message
	 * @param success
	 */
	public Result(String message, boolean success) {
		this.message = message;
		this.success = success;
	}
  }
}

@ApplicationScoped
class BookService{
  public void validateBook(@Valid Book book){
    //just showing I can do validate
  }
}

class Book{
  @NotBlank(message = "Title may not be blank")
  public String title;
  @NotBlank(message = "Author may not be blank")
  public String author;

  @Min(message ="Page can't be less than 10", value =10)
  public double pages;

/**
 * @param title
 * @param author
 * @param pages
 */
public Book(@NotBlank(message = "Title may not be blank") String title,
		@NotBlank(message = "Author may not be blank") String author,
		@Min(message = "Page can't be less than 10", value = 10) double pages) {
	this.title = title;
	this.author = author;
	this.pages = pages;
};
}