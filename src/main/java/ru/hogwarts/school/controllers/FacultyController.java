package ru.hogwarts.school.controllers;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @GetMapping // READ / GET http://localhost:8080/faculty
    public Collection<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }
    @PostMapping // CREATE / POST http://localhost:8080/faculty
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }
    @GetMapping("/{id}") // READ / GET http://localhost:8080/faculty/23
    public Faculty getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }
    @PutMapping("/{id}") // UPDATE / PUT http://localhost:8080/faculty/23
    public Faculty updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(id, faculty);
    }

    @DeleteMapping("/{id}") // DELETE http://localhost:8080/faculty/23
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
    @GetMapping(params = {"color"})
    public Collection<Faculty> getFacultiesByColor(@RequestParam String color) {
        return facultyService.getFacultiesByColor(color);
    }
}