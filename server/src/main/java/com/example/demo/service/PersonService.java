package com.example.demo.service;


import com.example.demo.dto.PersonDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PersonService {
    final RandomNames randomNames = new RandomNames();

    public List<PersonDto> getRandom(int amount){
        return IntStream.range(0, amount).mapToObj(i -> mapDto()).collect(Collectors.toList());
    }

    private PersonDto mapDto(){
        PersonDto dto = new PersonDto();
        dto.setName(randomNames.name());
        return dto;
    }

}
