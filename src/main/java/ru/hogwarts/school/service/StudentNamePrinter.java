package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentNamePrinter implements Printer {
    private final StudentRepository studentRepository;

    public StudentNamePrinter(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void print() {
        List<Student> studentList = studentRepository.findAll();

        System.out.println(studentList);

        printName(studentList.get(0));
        printName(studentList.get(1));

        new Thread(() -> {
            printName(studentList.get(2));
            printName(studentList.get(3));
        }).start();

        new Thread(() -> {
            printName(studentList.get(4));
            printName(studentList.get(5));
        }).start();

        printName(studentList.get(6));
        printName(studentList.get(7));
    }

    private void printName(Student student) {
        //private synchronized void printName(Student student) {
        try {
            System.out.println(student.getName());
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("exception");
        }
    }
}