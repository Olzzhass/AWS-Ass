package kaz.olzhas.spring.Project2SpringBoot.services;

import kaz.olzhas.spring.Project2SpringBoot.models.Book;
import kaz.olzhas.spring.Project2SpringBoot.models.Person;
import kaz.olzhas.spring.Project2SpringBoot.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class PeopleService {

    //logic of 10 days booking

    private final PersonRepository personRepository;

    @Autowired
    public PeopleService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    public Person findOne(int id){
        Optional<Person> person = personRepository.findById(id);

        return person.orElse(null);
    }

    @Transactional
    public List<Book> getBooks(int id){
        Optional<Person> person = personRepository.findById(id);

        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());

            List<Book> booksOfPerson = person.get().getBooks();

            for(Book book : booksOfPerson){
                if(book.getBorrow_date() != null && book.getBorrow_date().before(Date.from(Instant.now().minusSeconds(864000)))){
                    book.setExpired(true);
                }else{
                    book.setExpired(false);
                }
            }
            return booksOfPerson;
        }else{
            return Collections.emptyList();
        }
    }

    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);

        personRepository.save(updatedPerson);
    }
}
