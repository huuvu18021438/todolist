package io.example.todo_list.mapper;

import io.example.todo_list.model.Person;
import io.example.todo_list.rest.api.PersonResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonResource toResource(Person person);

}
