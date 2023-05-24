package io.example.todo_list.rest.controller;

import io.example.todo_list.mapper.ItemMapper;
import io.example.todo_list.model.Item;
import io.example.todo_list.rest.api.ItemPatchResource;
import io.example.todo_list.rest.api.ItemResource;
import io.example.todo_list.rest.api.ItemUpdateResource;
import io.example.todo_list.rest.api.NewItemResource;
import io.example.todo_list.service.ItemService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @PostMapping
    public Mono<Item> create(@RequestBody final NewItemResource newItemResource) {
        return itemService.create(itemMapper.toModel(newItemResource));
    }

    @PutMapping("/{id}")
    public Mono<Item> update(@PathVariable @NotNull final Long id,
                             @RequestHeader(value = HttpHeaders.IF_MATCH) final Long version,
                             @RequestBody final ItemUpdateResource itemUpdateResource) {
        return itemService.findById(id, version, false)
                .map(item -> itemMapper.update(itemUpdateResource, item))
                .flatMap(itemService::update);
    }

    @PatchMapping("/{id}")
    public Mono<Item> patch(@PathVariable @NotNull final Long id,
                            @RequestHeader(value = HttpHeaders.IF_MATCH) final Long version,
                            @RequestBody final ItemPatchResource patchResource) {
        return itemService.findById(id, version, true)
                .map(item -> itemMapper.patch(patchResource, item))
                .flatMap(itemService::update);
    }

    @GetMapping("/{id}")
    public Mono<ItemResource> findById(@PathVariable final Long id) {
        return itemService.findById(id, null, true).map(itemMapper::toResource);
    }

    @GetMapping
    public Flux<ItemResource> getAllItems() {
        return itemService.findAll().map(itemMapper::toResource);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable final Long id,
                             @RequestHeader(value = HttpHeaders.IF_MATCH) final Long version) {
        return itemService.deleteById(id, version);
    }
}
