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

        printName(studentList.get(count));
        printName(studentList.get(count));

        new Thread(() -> {
            printName(studentList.get(count));
            printName(studentList.get(count));
        }).start();

        new Thread(() -> {
            printName(studentList.get(count));
            printName(studentList.get(count));
        }).start();

        printName(studentList.get(count));
        printName(studentList.get(count));
    }

    //private void printName(Student student) {

    public int count = 0;
    public Object sinch = new Object();
    private void printName(Student student) {

        synchronized (sinch) {
        count++;
        }

            try {
                System.out.println(student.getName());
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("exception");
            }
        }
}
