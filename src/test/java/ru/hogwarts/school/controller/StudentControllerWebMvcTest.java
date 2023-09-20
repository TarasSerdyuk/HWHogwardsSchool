package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @Test
    public void testSaveStudent() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", 1L);
        studentObject.put("name", "test name");
        studentObject.put("age", 20);

        Student student = new Student(1L, "test name", 20);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test name"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    public void testGetAllStudents() throws Exception{
        when(studentRepository.findAll()).thenReturn(List.of(
                new Student(1L, "name1", 19),
                new Student(2L, "name2", 21)
        ));


        mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[0].age").value(19))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[1].age").value(21));

        Mockito.verify(studentRepository, Mockito.times(1)).findAll();
        Mockito.verify(studentService, Mockito.times(1)).getAllStudents();
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student(1L, "aaa", 30);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/" + student.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("aaa"))
                .andExpect(jsonPath("$.age").value(30));

        verify(studentRepository, times(1)).findById(1L);
        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    public void testGetStudentsByAge() throws Exception {
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "name", 21));
        studentList.add(new Student(2L, "name2", 19));

        when(studentRepository.findByAge(20)).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/student?age1=21"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStudentsByAgeBetween() throws Exception {
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "name", 18));
        studentList.add(new Student(2L, "name2", 24));

        when(studentRepository.findByAgeBetween(21, 30)).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/student?age1=21&age2=30"))
                .andExpect(status().isOk());
    }
}