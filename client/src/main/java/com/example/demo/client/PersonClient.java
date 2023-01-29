package com.example.demo.client;

import com.example.demo.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "person-client", url = "${person.client.url}")
public interface PersonClient {

    @GetMapping(value = "faker")
    List<PersonDto> getAll(@RequestParam(value = "amount", defaultValue = "10", required = false) int amount);

}
