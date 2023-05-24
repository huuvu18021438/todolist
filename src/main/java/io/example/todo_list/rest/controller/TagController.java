package io.example.todo_list.rest.controller;

import io.example.todo_list.mapper.TagMapper;
import io.example.todo_list.rest.api.TagResource;
import io.example.todo_list.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@Slf4j
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping("/{id}")
    public Mono<TagResource> findById(@PathVariable final Long id) {
        return tagService.findById(id)
                .map(tagMapper::toResource);
    }

    @GetMapping
    public Flux<TagResource> getAll() {
        return tagService.findAll()
                .map(tagMapper::toResource);
    }
}
