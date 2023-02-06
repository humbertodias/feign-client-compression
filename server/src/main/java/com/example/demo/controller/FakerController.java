package com.example.demo.controller;

import com.example.demo.dto.PersonDto;
import com.example.demo.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FakerController {

    final PersonService personService;

    public FakerController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("faker")
    List<PersonDto> getRandom(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount, @RequestParam(value = "delay", defaultValue = "0", required = false) int delay) throws InterruptedException{
        Thread.sleep(delay);
        return personService.getRandom(amount);
    }


}
