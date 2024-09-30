package dev.jpcenz.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(
        name = "notes",
        indexes = {
        @Index(name = "idx_note_archived", columnList = "archived")
        })
@Data
public class Note {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private boolean archived;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "note_category",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Fetch(FetchMode.JOIN)
    //@JsonIgnore
    private Set<Category> categories;
}
