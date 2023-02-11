package com.example.demo.service;


import com.example.demo.dto.PersonDto;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PersonService {

    final Faker faker = new Faker(new Locale("pt-BR"), new Random(0));

    public List<PersonDto> getRandom(int amount){
        return IntStream.range(0, amount).mapToObj(i -> mapDto()).collect(Collectors.toList());
    }

    private PersonDto mapDto(){
        PersonDto dto = new PersonDto();
//        dto.setName(faker.name().fullName());
//        dto.setAddress(faker.address().fullAddress());
        dto.setName(UUID.randomUUID().toString());
        dto.setAddress(UUID.randomUUID().toString());
        return dto;
    }

}
