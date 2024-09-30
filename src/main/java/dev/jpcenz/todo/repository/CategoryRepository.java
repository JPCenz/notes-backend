package dev.jpcenz.todo.repository;

import dev.jpcenz.todo.model.Category;
import dev.jpcenz.todo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Optional<Category> findByName(String name);




}
