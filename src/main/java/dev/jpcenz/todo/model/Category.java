package dev.jpcenz.todo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(name = "idx_category_name", columnList = "name")
        })
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
