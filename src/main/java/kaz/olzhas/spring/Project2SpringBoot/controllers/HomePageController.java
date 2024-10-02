package kaz.olzhas.spring.Project2SpringBoot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    //Only for redirecting

    @GetMapping("")
    public String homePage(){
        return "homePage";
    }
}
