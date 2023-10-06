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

        printName(0);
        printName(1);

        new Thread(() -> {
            printName(2);
            printName(3);
        }).start();

        new Thread(() -> {
            printName(4);
            printName(5);
        }).start();

        printName(6);
        printName(7);
    }




    private synchronized void printName(int count) {
        try {
        List<Student> students = studentRepository.findAll();
        System.out.println(students.get(count++ %students.size()).getName());
            Thread.sleep(2000);
        }
        catch (Exception e) {
                System.out.println("exception");
            }
        }
}
