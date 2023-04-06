package com.example.demo.controller;

import com.example.demo.dto.PersonDto;
import com.example.demo.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController {

    final PersonService personService;

    public DemoController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("person")
    List<PersonDto> getPersonSync(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount,
                                  @RequestParam(value = "delay", defaultValue = "0", required = false) int delay,
                                  @RequestParam(value = "cacheManager", defaultValue = "noCacheConfiguration", required = false) String cacheManager) {
        return personService.getPersonSync(amount, delay, cacheManager);
    }

    @GetMapping("server-url")
    public String getPersonClientUrl() {
        return personService.getPersonClientUrl();
    }

    @GetMapping("env")
    public Map<String, String> getEnv() {
        return System.getenv();
    }

//    @GetMapping("person-async-flux")
//    public Mono<List<PersonDto>> getRandomPersonAsyncFlux() throws ExecutionException, InterruptedException {
//        var one =  personService.getAllAsync(99, 1000);
//        var two =  personService.getAllAsync(99, 1000);
//        return Flux
//                .concat(Flux.fromIterable(one.get()),Flux.fromIterable(two.get()))
//                .collectList();
//    }

}
