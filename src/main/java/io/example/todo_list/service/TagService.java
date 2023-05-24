package io.example.todo_list.service;

import io.example.todo_list.model.Tag;
import io.example.todo_list.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TagService {
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("name"));

    private final TagRepository tagRepository;

    public Flux<Tag> findAll() {
        return tagRepository.findAll(DEFAULT_SORT);
    }

    public Mono<Tag> findById(final Long id) {
        return tagRepository.findById(id);
    }
}
