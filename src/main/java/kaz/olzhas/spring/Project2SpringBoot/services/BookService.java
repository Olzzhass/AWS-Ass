package kaz.olzhas.spring.Project2SpringBoot.services;

import kaz.olzhas.spring.Project2SpringBoot.models.Book;
import kaz.olzhas.spring.Project2SpringBoot.models.Person;
import kaz.olzhas.spring.Project2SpringBoot.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class BookService {

    //logic of 10 days booking

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sort_by_year){
        return bookRepository.findAll(Sort.by("publishedYear"));
    }

    public List<Book> findAllWithPagination(int page, int books_per_page, boolean sort_by_year){
        return bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("publishedYear"))).getContent();
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    public Book findOne(int id){
        Optional<Book> book = bookRepository.findById(id);

        return book.orElse(null);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);

        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    @Transactional
    public void assign(Book book, Person person){
         book.setOwner(person);
         book.setBorrow_date(new Date());

         bookRepository.save(book);
    }

    @Transactional
    public void release(Book book, int id){
        book.setOwner(null);

        bookRepository.save(book);
    }

    public List<Book> searchBookByName(String name){
        return bookRepository.findByNameOfBookStartingWith(name);
    }
}
