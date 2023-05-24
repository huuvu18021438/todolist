package io.example.todo_list.service;

import io.example.todo_list.model.Person;
import io.example.todo_list.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonService {
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("first_name, last_name"));

    private final PersonRepository personRepository;

    public Flux<Person> findAll() {
        return personRepository.findAll(DEFAULT_SORT);
    }

    public Mono<Person> findById(final Long id) {
        return personRepository.findById(id);
    }
}
