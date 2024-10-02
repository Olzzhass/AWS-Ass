package kaz.olzhas.spring.Project2SpringBoot.controllers;

import jakarta.validation.Valid;
import kaz.olzhas.spring.Project2SpringBoot.models.Person;
import kaz.olzhas.spring.Project2SpringBoot.services.BookService;
import kaz.olzhas.spring.Project2SpringBoot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/people")
public class PeoplePageController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public PeoplePageController(BookService bookService, PeopleService peopleService){
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll());

        return "people/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("person", new Person());

        return "people/create";
    }

    @PostMapping
    public String createNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        //Validator optional

        if(bindingResult.hasErrors()){
            return "people/create";
        }

        //logic of saving to the database
        peopleService.save(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.getBooks(id));

        return "people/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", peopleService.findOne(id));

        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        //person validator optional

        if(bindingResult.hasErrors()){
            return "people/edit";
        }

        peopleService.update(id, person);

        return "redirect:/people";
    }
}
