package io.example.todo_list.rest.api;

import lombok.Data;

@Data
public class PersonResource {
    private Long id;
    private String firstName;
    private String lastName;
}
