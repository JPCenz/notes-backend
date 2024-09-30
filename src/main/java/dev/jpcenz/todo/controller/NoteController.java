package dev.jpcenz.todo.controller;

import dev.jpcenz.todo.model.Category;
import dev.jpcenz.todo.model.Note;
import dev.jpcenz.todo.service.INoteService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.processing.SupportedSourceVersion;
import javax.print.attribute.URISyntax;
import java.net.URI;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final INoteService noteService;

    public NoteController(INoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Obtiene todas las notas, con la opción de filtrar por estado de archivado.
     *
     * @param archived \- Si es true, devuelve solo las notas archivadas; de lo contrario, devuelve solo las notas no archivadas.
     * @return ResponseEntity con un mapa que contiene la lista de notas.
     */
    @GetMapping("/")
    public ResponseEntity<Map<String,Object>>getNotes(@RequestParam(required = false) boolean archived) {
        return ResponseEntity.ok(Map.of("notes", noteService.getNotes(archived)));
    }
    /**
     * Guarda una nueva nota.
     *
     * @param note \- La nota a guardar.
     * @return ResponseEntity con la URI de la nota creada.
     */
    @PostMapping("/")
    public ResponseEntity<?> saveNote(@RequestBody Note note) {
        log.info("saveNote"+note.getTitle());
        log.info("saveNote"+note.getContent());
        if (note == null || note.getTitle() == null || note.getTitle().isEmpty()){
            return new ResponseEntity<Map<String,Object>>(Map.of("message", "Data is required"), HttpStatus.BAD_REQUEST);
        }

        Note ncreated = noteService.save(note);
        return ResponseEntity.created(URI.create("/api/notes/"+ncreated.getId())).build();
    }
    /**
     * Actualiza una nota existente.
     *
     * @param id \- El ID de la nota a actualizar.
     * @param note \- La nota con los nuevos datos.
     * @return ResponseEntity indicando el resultado de la operación.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody Note note) {
        log.info("updateNote"+note.getTitle());
        Note n = noteService.getNoteById(id);
        if (n == null){
            return new ResponseEntity<Map<String,Object>>(Map.of("message", "Note not found"), HttpStatus.NOT_FOUND);
        }
        noteService.update(n.getId(), note);
        return ResponseEntity.ok().build();
    }
    /**
     * Elimina una nota por su ID.
     *
     * @param id \- El ID de la nota a eliminar.
     * @return ResponseEntity indicando el resultado de la operación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        log.info("deleteNote" + id);
        if (noteService.getNoteById(id) == null) {
            return new ResponseEntity<Map<String, Object>>(Map.of("message", "Note not found"), HttpStatus.NOT_FOUND);
        }
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Archiva una nota por su ID.
     *
     * @param id \- El ID de la nota a archivar.
     * @return ResponseEntity indicando el resultado de la operación.
     */
    @PutMapping("/{id}/archive")
    public ResponseEntity<?> archive(@PathVariable Long id) {
        log.info("archive" + id);
        Note note = noteService.getNoteById(id);
        if (note == null) {
            return new ResponseEntity<Map<String, Object>>(Map.of("message", "Note not found"), HttpStatus.NOT_FOUND);
        }
        note.setArchived(true);
        noteService.save(note);
        return ResponseEntity.ok().build();
    }
    /**
     * Desarchiva una nota por su ID.
     *
     * @param id \- El ID de la nota a desarchivar.
     * @return ResponseEntity indicando el resultado de la operación.
     */
    @PutMapping("/{id}/unarchive")
    public ResponseEntity<?> unarchive(@PathVariable Long id) {
        log.info("unarchive" + id);
        Note note = noteService.getNoteById(id);
        if (note == null) {
            return new ResponseEntity<Map<String, Object>>(Map.of("message", "Note not found"), HttpStatus.NOT_FOUND);
        }
        note.setArchived(false);
        noteService.save(note);
        return ResponseEntity.ok().build();
    }
    /**
     * Añade una categoría a una nota por el nombre de la categoría.
     *
     * @param noteId \- El ID de la nota.
     * @param categoryName \- El nombre de la categoría a añadir.
     * @return ResponseEntity con la nota actualizada.
     */
    @PutMapping("/{noteId}/categories/name/{categoryName}")
    public ResponseEntity<?> addCategoryByName(@PathVariable Long noteId, @PathVariable String categoryName) {
        Note updatedNote = noteService.addCategoryByName(noteId, categoryName);
        if (updatedNote == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Note or category not found"));
        }
        return ResponseEntity.ok(updatedNote);
    }
    /**
     * Elimina una categoría de una nota por el nombre de la categoría.
     *
     * @param noteId \- El ID de la nota.
     * @param categoryName \- El nombre de la categoría a eliminar.
     * @return ResponseEntity con la nota actualizada.
     */
    @DeleteMapping("/{noteId}/categories/name/{categoryName}")
    public ResponseEntity<?> removeCategoryByName(@PathVariable Long noteId, @PathVariable String categoryName) {
        Note updatedNote = noteService.removeCategoryByName(noteId, categoryName);
        if (updatedNote == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Note or category not found"));
        }
        return ResponseEntity.ok(updatedNote);
    }

    /**
     * Obtiene las notas por el nombre de la categoría.
     *
     * @param categoryName \- El nombre de la categoría.
     * @return ResponseEntity con la lista de notas que pertenecen a la categoría.
     */
    @GetMapping("/categories/{categoryName}")
    public ResponseEntity<?> getNotesByCategoryName(@PathVariable String categoryName) {
        return ResponseEntity.ok(noteService.getNotesByCategoryName(categoryName));
    }
    /**
     * Obtiene las categorías de una nota por su ID.
     *
     * @param noteId \- El ID de la nota.
     * @return ResponseEntity con el conjunto de categorías de la nota.
     */

    @GetMapping("/{noteId}/categories")
    public ResponseEntity<Set<Category>> getCategoriesByNoteId(@PathVariable Long noteId) {
        Note note = noteService.getNoteById(noteId);
        if (note == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(note.getCategories());
    }

}
