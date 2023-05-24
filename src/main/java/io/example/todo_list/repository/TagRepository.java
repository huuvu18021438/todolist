package io.example.todo_list.repository;

import io.example.todo_list.model.Tag;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TagRepository extends R2dbcRepository<Tag, Long> {
    @Query("select t.* from tags t join items_tags it on t.id = it.tag_id where it.item_id = :item_id order by t.name")
    Flux<Tag> findTagsByItemId(Long itemId);
}
