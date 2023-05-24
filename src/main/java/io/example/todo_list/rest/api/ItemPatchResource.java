package io.example.todo_list.rest.api;

import io.example.todo_list.model.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Optional;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ItemPatchResource {

    private Optional<@NotBlank @Size(max=4000) String> description;
    private Optional<@NotNull ItemStatus> status;
    private Optional<Long> assigneeId;
    private Optional<Set<Long>> tagIds;

}
