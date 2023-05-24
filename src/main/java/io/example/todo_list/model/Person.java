package io.example.todo_list.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("persons")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Person {

    @Id
    private Long id;

    @Version
    private Long version;

    @Size(max=100)
    @NotBlank
    private String firstName;

    @Size(max=100)
    @NotBlank
    private String lastName;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
