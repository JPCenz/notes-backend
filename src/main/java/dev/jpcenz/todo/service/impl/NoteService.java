package dev.jpcenz.todo.service.impl;

import dev.jpcenz.todo.model.Category;
import dev.jpcenz.todo.model.Note;
import dev.jpcenz.todo.repository.CategoryRepository;
import dev.jpcenz.todo.repository.NoteRepository;
import dev.jpcenz.todo.service.INoteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService implements INoteService {

    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    public NoteService(NoteRepository noteRepository, CategoryRepository categoryRepository) {
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }
    public List<Note> getNotes(boolean archived) {
        return noteRepository.findByArchivedOrderByIdDesc(archived);
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Note update(Long id, Note note) {
        return noteRepository.findById(id)
                .map(n -> {
                    n.setTitle(note.getTitle());
                    n.setContent(note.getContent());
                    n.setArchived(note.isArchived());
                    return noteRepository.save(n);
                })
                .orElse(null);
    }

    @Override
    public Note addCategoryByName(Long noteId, String categoryName) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note != null) {
            Category category = categoryRepository.findByName(categoryName)
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(categoryName);
                        return categoryRepository.save(newCategory);
                    });
            note.getCategories().add(category);

            return noteRepository.save(note);
        }
        return null;
    }

    public Note removeCategoryByName(Long noteId, String categoryName) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note != null) {
            Category category = categoryRepository.findByName(categoryName).orElse(null);
            if (category != null) {
                note.getCategories().remove(category);
                return noteRepository.save(note);
            }
        }
        return null;
    }


    public List<Note> getNotesByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new RuntimeException("Category not found"));
        return noteRepository.findNoteByCategoriesContaining(category);
    }


}
