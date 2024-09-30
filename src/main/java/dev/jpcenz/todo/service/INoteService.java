package dev.jpcenz.todo.service;

import dev.jpcenz.todo.model.Category;
import dev.jpcenz.todo.model.Note;
import java.util.List;

public interface INoteService {

    List<Note> getNotes();

    List<Note> getNotes(boolean archived);

    Note getNoteById(Long id);

    Note save(Note note);

    void delete(Long id);

    Note update(Long id, Note note);

    Note addCategoryByName(Long id, String category);

    Note removeCategoryByName(Long noteId, String categoryName);

    List<Note> getNotesByCategoryName(String categoryName);
}
