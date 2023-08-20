package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty getFacultyById(Long id);

    Collection<Faculty> getAllFaculties();

    Faculty updateFaculty(Long id, Faculty faculty);

    void deleteFaculty(Long id);

    Collection<Faculty> getFacultiesByColor(String color);
}