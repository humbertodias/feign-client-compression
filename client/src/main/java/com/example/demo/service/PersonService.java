package com.example.demo.service;


import com.example.demo.client.PersonClient;
import com.example.demo.dto.PersonDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PersonService {

    final
    PersonClient personClient;

    @Value("${person.client.host}")
    String personClientUrl;
    public PersonService(PersonClient personClient) {
        this.personClient = personClient;
    }

    private static final String PATTERN_FORMAT = "dd.MM.yyyy";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
            .withZone(ZoneId.systemDefault());

    public List<PersonDto> getAll(int amount, int delay){
        return personClient.getAll(amount, delay, dataBase());
    }

    private String dataBase(){
        return formatter.format(Instant.now());
    }

    public CompletableFuture<List<PersonDto>> getAllAsync(int amount, int delay){
        return CompletableFuture.supplyAsync(()->personClient.getAll(amount, delay, dataBase()));
    }

    public String getPersonClientUrl(){
        return personClientUrl;
    }

}
