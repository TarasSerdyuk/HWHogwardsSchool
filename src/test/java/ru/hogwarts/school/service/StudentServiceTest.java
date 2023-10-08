package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

public class StudentServiceTest {
    private StudentService studentService;
    private StudentRepository studentRepository;
    @BeforeEach
    public void setUp() {
        studentRepository = Mockito.mock(StudentRepository.class);
        studentService = new StudentServiceImpl(studentRepository);
    }
    @Test
    public void shouldReturnStudentByIdWhenStudentExists() {
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student("nameTest", 11)));
        Student student = studentService.getStudentById(1L);
        Mockito.verify(studentRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void shouldThrowExceptionWhenStudentNotExist() {
        Assertions.assertThrows(StudentNotFoundException.class, () ->
                studentService.getStudentById(1L));
    }
}