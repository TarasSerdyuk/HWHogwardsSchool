package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Student addStudent(Student student) {
        logger.debug("Student is added: {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        logger.debug("Student get by id {}", id);
        return studentRepository.findById(id).orElseThrow(() ->new StudentNotFoundException( "Student Not Found"));
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.debug("Get all students");
        return studentRepository.findAll();
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        logger.debug("Update student by id {}: {}", id, student);
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.debug("Student delete by id {}", id);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getStudentsByAge(int age) {
        logger.debug("Get students with age {}", age);
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> getStudentsByAgeBetween(Integer min, Integer max) {
        logger.debug("Get students between age {} and {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }
    @Override
    public Integer getCountOfStudents() {
        logger.debug("Get count of all students");
        return studentRepository.getCountOfStudents();
    }

    @Override
    public Integer getAverageAgeOfStudents() {
        logger.debug("Get average age of students");
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.debug("Get last 5 students");
        return studentRepository.getLastFiveStudents();
    }
}
