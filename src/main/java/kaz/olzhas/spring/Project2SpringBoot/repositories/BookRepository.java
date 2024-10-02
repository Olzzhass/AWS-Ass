package kaz.olzhas.spring.Project2SpringBoot.repositories;

import kaz.olzhas.spring.Project2SpringBoot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameOfBookStartingWith(String name);
}
