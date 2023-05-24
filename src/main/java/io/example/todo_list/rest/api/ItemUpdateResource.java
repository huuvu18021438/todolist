package io.example.todo_list.rest.api;

import io.example.todo_list.model.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class ItemUpdateResource {

    @NotBlank
    @Size(max=4000)
    private String description;

    @NotNull
    private ItemStatus status;

    private Long assigneeId;

    private Set<Long> tagIds;
}

