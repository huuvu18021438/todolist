package io.example.todo_list.rest.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class NewItemResource {
    @NotBlank
    @Size(max=4000)
    private String description;

    private Long assigneeId;

    private Set<Long> tagIds;
}
