package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentNamePrinter implements Printer {
    private final StudentRepository studentRepository;
    private int count = 0;
    public StudentNamePrinter(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void print() {
        List<Student> studentList = studentRepository.findAll();

        System.out.println(studentList);

        printName(studentList);
        printName(studentList);

        new Thread(() -> {
            printName(studentList);
            printName(studentList);
        }).start();

        new Thread(() -> {
            printName(studentList);
            printName(studentList);
        }).start();

        printName(studentList);
        printName(studentList);
    }




    private synchronized void printName(List<Student> studentList) {
        try {
               System.out.println(studentList.get(count++ % studentList.size()).getName());
            Thread.sleep(2000);
        }
        catch (Exception e) {
                System.out.println("exception");
            }
        }
}
