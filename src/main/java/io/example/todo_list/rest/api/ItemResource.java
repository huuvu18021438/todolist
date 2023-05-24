package io.example.todo_list.rest.api;

import io.example.todo_list.model.ItemStatus;
import io.example.todo_list.model.Tag;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class ItemResource {
    private Long id;
    private Long version;

    private String description;
    private ItemStatus status;

    private PersonResource assignee;
    private List<Tag> tags;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
