package com.example.demo.service;


import com.example.demo.client.PersonClient;
import com.example.demo.dto.PersonDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public List<PersonDto> getAll(int amount, int delay){
        return personClient.getAll(amount, delay);
    }

    public CompletableFuture<List<PersonDto>> getAllAsync(int amount, int delay){
        return CompletableFuture.supplyAsync(()->personClient.getAll(amount, delay));
    }

    public String getPersonClientUrl(){
        return personClientUrl;
    }

}
