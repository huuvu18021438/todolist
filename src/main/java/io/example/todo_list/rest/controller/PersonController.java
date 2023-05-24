package io.example.todo_list.rest.controller;

import io.example.todo_list.mapper.PersonMapper;
import io.example.todo_list.rest.api.PersonResource;
import io.example.todo_list.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @GetMapping("/{id}")
    public Mono<PersonResource> findById(@PathVariable final Long id) {
        return personService.findById(id).map(personMapper::toResource);
    }

    @GetMapping
    public Flux<PersonResource> getAll() {
        return personService.findAll()
                .map(personMapper::toResource);
    }
}
