package ru.hogwarts.school.controllers;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping(params = {"age"})
    public Collection<Student> getStudentByAge(@RequestParam int age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping(params = {"min","max"})
    public Collection<Student> getStudentByAgeBetween(@RequestParam Integer min, Integer max) {
        return studentService.getStudentsByAgeBetween(min, max);
    }
    @GetMapping("/count")
    public Integer getStudentCount() {
        return studentService.getCountOfStudents();
    }

    @GetMapping("/average-age")
    public Integer getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/last-5-students")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

}