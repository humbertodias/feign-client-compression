package com.example.demo.controller;

import com.example.demo.dto.PersonDto;
import com.example.demo.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/demo")
public class DemoController {

    final PersonService personService;

    public DemoController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("person")
    List<PersonDto> getRandomPerson(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount, @RequestParam(value = "delay", defaultValue = "0", required = false) int delay){
        return personService.getAll(amount, delay);
    }

    @GetMapping("person-sync")
    List<PersonDto> getRandomPersonSync() {
        var one =  personService.getAll(99, 1000);
        var two =  personService.getAll(99, 1000);
        return Stream.concat(one.stream() , two.stream())
                .collect(Collectors.toList());
    }

    @GetMapping("person-async")
    List<PersonDto> getRandomPersonAsync() throws ExecutionException, InterruptedException {
        var one =  personService.getAllAsync(99, 1000);
        var two =  personService.getAllAsync(99, 1000);
        return Stream.concat(one.get().stream() , two.get().stream())
                .collect(Collectors.toList());
    }

    @GetMapping("server-url")
    public String getPersonClientUrl(){
        return personService.getPersonClientUrl();
    }

    @GetMapping("env")
    public Map<String,String> getEnv(){
        return System.getenv();
    }

//    @GetMapping("person-async-flux")
//    Mono<List<PersonDto>> getRandomPersonAsyncFlux() throws ExecutionException, InterruptedException {
//        var one =  personService.getAllAsync(99, 1000);
//        var two =  personService.getAllAsync(99, 1000);
//        return Flux
//                .concat(Flux.fromIterable(one.get()),Flux.fromIterable(two.get()))
//                .collectList();
//    }

}
