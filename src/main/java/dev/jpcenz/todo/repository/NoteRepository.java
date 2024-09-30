package dev.jpcenz.todo.repository;

import dev.jpcenz.todo.model.Category;
import dev.jpcenz.todo.model.Note;
import jakarta.persistence.OrderBy;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @EntityGraph(attributePaths = "categories")
    List<Note> findAll();
    @EntityGraph(attributePaths = "categories")
    List<Note> findByArchivedOrderByIdDesc(boolean archived);
    List<Note> findNoteByCategoriesContaining(Category category);
    List<Note> findNoteByCategoriesId(Long id);



}
