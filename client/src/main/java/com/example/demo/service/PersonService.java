package com.example.demo.service;


import com.example.demo.client.PersonClient;
import com.example.demo.dto.PersonDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    final
    PersonClient personClient;

    public PersonService(PersonClient personClient) {
        this.personClient = personClient;
    }

    public List<PersonDto> getAll(int amount){
        return personClient.getAll(amount);
    }

}
