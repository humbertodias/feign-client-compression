package com.example.demo.service;


import com.example.demo.client.PersonClient;
import com.example.demo.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PersonService {
    final
    PersonClient personClient;

    @Value("${person.client.host}")
    String personClientUrl;

    public PersonService(PersonClient personClient) {
        this.personClient = personClient;
    }

    public List<PersonDto> getPersonSync(int amount, int delay, String cacheManager) {
        return personClient.getPerson(amount, delay, cacheManager);
    }

    public String getPersonClientUrl() {
        return personClientUrl;
    }

}
