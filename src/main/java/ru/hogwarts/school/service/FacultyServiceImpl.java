package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;


@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.debug("Faculty is added: {}", faculty);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFacultyById(Long id) {
        logger.debug("Get faculty by id {}", id);
        return facultyRepository.findById(id).
                orElseThrow(() ->new FacultyNotFoundException( "Faculty Not Found"));
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.debug("Get all faculties");
        return facultyRepository.findAll();
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        logger.debug("Update faculty by id {}: {}", id, faculty);
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.debug("Delete faculty by id {}", id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.debug("Get faculty by color {}", color);
       return facultyRepository.findByColor(color);
   }
    @Override
    public Collection<Faculty> findFacultyByNameOrColor(String name, String color) {
        logger.debug("Get faculty by name {} or color {}", name, color);
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}
