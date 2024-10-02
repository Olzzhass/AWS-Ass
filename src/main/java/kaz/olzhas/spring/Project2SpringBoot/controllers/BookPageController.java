package kaz.olzhas.spring.Project2SpringBoot.controllers;

import jakarta.validation.Valid;
import kaz.olzhas.spring.Project2SpringBoot.models.Book;
import kaz.olzhas.spring.Project2SpringBoot.models.Person;
import kaz.olzhas.spring.Project2SpringBoot.services.BookService;
import kaz.olzhas.spring.Project2SpringBoot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookPageController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookPageController(BookService bookService, PeopleService peopleService){
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model,
           @RequestParam(value = "page", required = false) Integer page,
           @RequestParam(value = "books_per_page", required = false) Integer books_per_page,
           @RequestParam(value = "sort_by_year", required = false) boolean sort_by_year
                        ){

        if(page == null || books_per_page == null){
            model.addAttribute("books", bookService.findAll(sort_by_year)); //All books by default sorting
        }else{
            model.addAttribute("books", bookService.findAllWithPagination(page, books_per_page, sort_by_year));
        }

//        model.addAttribute("books", bookService.findAll(PageRequest.of(page, books_per_page, Sort.by("sort_by_year"))));

        return "book/index";
    }

    @GetMapping("/create")
    public String newBook(Model model){
        model.addAttribute("book", new Book());

        return "book/create";
    }

    @PostMapping
    public String createNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        //book validator optional

        if(bindingResult.hasErrors()){
            return "book/create";
        }

        book.setBorrow_date(new Date());

        bookService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookService.findOne(id));

        Book book = bookService.findOne(id);
        Optional<Person> owner = Optional.ofNullable(book.getOwner());

        if(owner.isPresent()){
            model.addAttribute("owner", owner.get());
        }else{
            model.addAttribute("people", peopleService.findAll());   //for выпадающий список
        }

        return "book/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookService.findOne(id));

        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){
        //logic of validator

        if(bindingResult.hasErrors()){
            return "book/edit";
        }

        bookService.update(id, book);

        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);

        return "redirect:/books";
    }

    @PatchMapping("/set/{id}")
    public String setPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        Book book = bookService.findOne(id);

        bookService.assign(book, person);

        return "redirect:/books";
    }

    @PatchMapping("{id}/release")
    public String release(@PathVariable("id") int id){
        Book book = bookService.findOne(id);

        bookService.release(book, id);

        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(){
        return "book/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("books", bookService.searchBookByName(query));

        return "book/search";
    }
}
