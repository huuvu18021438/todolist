package io.example.todo_list.service;

import io.example.todo_list.exception.ItemNotFoundException;
import io.example.todo_list.exception.UnexpectedItemVersionException;
import io.example.todo_list.mapper.TagMapper;
import io.example.todo_list.model.Item;
import io.example.todo_list.model.ItemTag;
import io.example.todo_list.repository.ItemRepository;
import io.example.todo_list.repository.ItemTagRepository;
import io.example.todo_list.repository.PersonRepository;
import io.example.todo_list.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("lastModifiedDate"));

    private final ItemRepository itemRepository;
    private final PersonRepository personRepository;
    private final ItemTagRepository itemTagRepository;
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;


    public Flux<Item> findAll() {
        return itemRepository.findAll(DEFAULT_SORT)
                .flatMap(this::loadRelations);
    }

    @Transactional
    public Mono<Item> create(Item item) {
        if (item.getId() != null || item.getVersion() != null) {
            return Mono.error(new IllegalArgumentException("When creating an item, the id and the version must be null"));
        }

        return // Save the new item
                itemRepository.save(item)
                .flatMap(savedItem ->
                        itemTagRepository.saveAll(tagMapper.toItemTags(savedItem.getId(), savedItem.getTags()))
                                .collectList()
                                .then(Mono.just(savedItem)));
    }


    public Mono<Item> update(Item itemToSave) {
        if (itemToSave.getId() == null || itemToSave.getVersion() == null) {
            return Mono.error(new IllegalArgumentException("When updating an item, the id and the version must be provided"));
        }

        return verifyExistence(itemToSave.getId())
                .then(itemTagRepository.findAllByItemId(itemToSave.getId()).collectList())
                .flatMap(currentItemTags -> {
                    final Collection<Long> existTagIds = tagMapper.extractTagIdsFromItemTags(currentItemTags);
                    final Collection<Long> tagIdsToSave = tagMapper.extractTagIdsFromTags(itemToSave.getTags());

                    final Collection<ItemTag> removedItemTags = currentItemTags.stream()
                            .filter(itemTag -> !tagIdsToSave.contains(itemTag.getTagId()))
                            .collect(Collectors.toList());

                    final Collection<ItemTag> addedItemTags = tagIdsToSave.stream()
                            .filter(tagId -> !existTagIds.contains(tagId))
                            .map(tagId -> new ItemTag(itemToSave.getId(), tagId))
                            .collect(Collectors.toList());

                    return itemTagRepository.deleteAll(removedItemTags)
                            .then(itemTagRepository.saveAll(addedItemTags).collectList());
                })
                .then(itemRepository.save(itemToSave));
    }

    public Mono<Void> deleteById(final Long id, final Long version) {
        return findById(id, version, false)
                .zipWith(itemTagRepository.deleteAllByItemId(id))
                .map(Tuple2::getT1)
                .flatMap(itemRepository::delete);
    }

    public Mono<Item> findById(final Long id, final Long version, final boolean loadRelations) {
        final Mono<Item> itemMono = itemRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(id)))
                .handle((item, sink) -> {
                    if (version != null && !version.equals(item.getVersion())) {
                        sink.error(new UnexpectedItemVersionException(version, item.getVersion()));
                    } else {
                        sink.next(item);
                    }
                });

        return loadRelations ? itemMono.flatMap(this::loadRelations) : itemMono;
    }

    private Mono<Item> loadRelations(final Item item) {
        //Load the tags
        Mono<Item> mono = Mono.just(item)
                .zipWith(tagRepository.findTagsByItemId(item.getId()).collectList())
                .map(result -> result.getT1().setTags(result.getT2()));

        //Load the assignee
        if (item.getAssigneeId() != null) {
            mono = mono.zipWith(personRepository.findById(item.getAssigneeId()))
                    .map(result -> result.getT1().setAssignee(result.getT2()));
        }

        return mono;
    }

    private Mono<Boolean> verifyExistence(Long id) {
        return itemRepository.existsById(id).handle((exists, sink) -> {
            if (Boolean.FALSE.equals(exists)) {
                sink.error(new ItemNotFoundException(id));
            } else {
                sink.next(exists);
            }
        });
    }
}
