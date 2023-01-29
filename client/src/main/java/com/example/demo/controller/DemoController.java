package com.example.demo.controller;

import com.example.demo.dto.PersonDto;
import com.example.demo.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    final PersonService personService;

    public DemoController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("person")
    List<PersonDto> getRandomPerson(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount){
        return personService.getAll(amount);
    }

}
