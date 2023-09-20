package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class StudentControllerRestTemplateIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testPostStudent() throws Exception {
        Student testStudent = new Student("test student", 30);

        ResponseEntity<Student> newStudentResponse =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent, Student.class);

        Assertions.assertThat(newStudentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(newStudentResponse.getBody()).isNotNull();
    }

    @Test
    public void testGetStudent() throws Exception {
        Student testStudent = new Student("test student", 30);

        ResponseEntity<Student> newStudentResponse =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent, Student.class);

        Assertions.assertThat(newStudentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student newStudent = newStudentResponse.getBody();

        ResponseEntity<Student[]> studentEntity =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student", Student[].class);

        Assertions.assertThat(studentEntity.getBody()).isNotNull();
        Assertions.assertThat(studentEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(studentEntity.getBody()).contains(newStudent);
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student testStudent = new Student("test student", 30);

        ResponseEntity<Student> newStudentResponse =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent, Student.class);

        Assertions.assertThat(newStudentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student newStudent = newStudentResponse.getBody();

        ResponseEntity<Student> studentEntity =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class);

        Assertions.assertThat(studentEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(studentEntity.getBody()).isNotNull();
        Assertions.assertThat(studentEntity.getBody().getAge()).isEqualTo(newStudent.getAge());
        Assertions.assertThat(studentEntity.getBody().getName()).isEqualTo(newStudent.getName());
        Assertions.assertThat(studentEntity.getBody().getId()).isEqualTo(newStudent.getId());
    }

    @Test
    public void testGetStudentByAge() throws Exception {
        Student testStudent1 = new Student("test student 1", 20);
        Student testStudent2 = new Student("test student 2", 25);
        Student testStudent3 = new Student("test student 3", 23);
        Student testStudent4 = new Student("test student 4", 20);

        int age = testStudent1.getAge();

        ResponseEntity<Student> newStudentResponse1 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent1, Student.class);
        ResponseEntity<Student> newStudentResponse2 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent2, Student.class);
        ResponseEntity<Student> newStudentResponse3 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent3, Student.class);
        ResponseEntity<Student> newStudentResponse4 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent4, Student.class);

        Assertions.assertThat(newStudentResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(newStudentResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(newStudentResponse3.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(newStudentResponse4.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student newStudent1 = newStudentResponse1.getBody();
        Student newStudent2 = newStudentResponse2.getBody();
        Student newStudent3 = newStudentResponse3.getBody();
        Student newStudent4 = newStudentResponse4.getBody();

        ResponseEntity<Student[]> studentEntity =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student?age1=" + age, Student[].class);

        Assertions.assertThat(studentEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(studentEntity.getBody()).isNotNull();
        Assertions.assertThat(studentEntity.getBody()).contains(newStudent1);
        Assertions.assertThat(studentEntity.getBody()).contains(newStudent4);
        Assertions.assertThat(studentEntity.getBody()).doesNotContain(newStudent2);
        Assertions.assertThat(studentEntity.getBody()).doesNotContain(newStudent3);

    }

    @Test
    public void testGetStudentByName() throws Exception {
        Student testStudent1 = new Student("nana", 20);
        Student testStudent2 = new Student("brus", 25);
        Student testStudent3 = new Student("nana", 23);
        Student testStudent4 = new Student("lara", 20);

        String name = "nana";

        ResponseEntity<Student> newStudentResponse1 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent1, Student.class);
        ResponseEntity<Student> newStudentResponse2 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent2, Student.class);
        ResponseEntity<Student> newStudentResponse3 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent3, Student.class);
        ResponseEntity<Student> newStudentResponse4 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent4, Student.class);

        Assertions.assertThat(newStudentResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(newStudentResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(newStudentResponse3.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(newStudentResponse4.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student newStudent1 = newStudentResponse1.getBody();
        Student newStudent2 = newStudentResponse2.getBody();
        Student newStudent3 = newStudentResponse3.getBody();
        Student newStudent4 = newStudentResponse4.getBody();

        ResponseEntity<Student[]> studentEntity =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student?name=" + name, Student[].class);

        Assertions.assertThat(studentEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(studentEntity.getBody()).isNotNull();
        Assertions.assertThat(studentEntity.getBody()).contains(newStudent1);
        Assertions.assertThat(studentEntity.getBody()).contains(newStudent3);
        Assertions.assertThat(studentEntity.getBody()).doesNotContain(newStudent2);
        Assertions.assertThat(studentEntity.getBody()).doesNotContain(newStudent4);
    }

    @Test
    public void testPutStudentById() throws Exception {
        Student testStudent = new Student("test student", 30);
        ResponseEntity<Student> newStudentResponse =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent, Student.class);

        Assertions.assertThat(newStudentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student newStudent = newStudentResponse.getBody();
        newStudent.setAge(31);
        newStudent.setName("test student 2");
        //Student updateStudent = new Student("test student 2", 31);

        HttpEntity<Student> newStudentEntity = new HttpEntity<>(newStudent, newStudentResponse.getHeaders());

        ResponseEntity<Student> studentEntity =
                testRestTemplate.exchange("http://localhost:" + port + "/student/" + newStudent.getId(),
                        HttpMethod.PUT, newStudentEntity, Student.class);

        Assertions.assertThat(studentEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(studentEntity.getBody()).isNotNull();
        Assertions.assertThat(studentEntity.getBody().getId()).isEqualTo(newStudent.getId());
        Assertions.assertThat(studentEntity.getBody().getName()).isEqualTo(newStudent.getName());
        Assertions.assertThat(studentEntity.getBody().getAge()).isEqualTo(newStudent.getAge());

//        ResponseEntity<Student> studentEntity =
//                testRestTemplate.put("http://localhost:" + port + "/student/" + newStudent.getId(), newStudent, Student.class);

//        ResponseEntity<Student> studentEntity =
//                testRestTemplate.exchange("http://localhost:" + port + "/student/" + newStudent.getId(),
//                        HttpMethod.PUT, updateStudent, Student.class);

//        ResponseEntity<Student> studentEntity =
//                testRestTemplate.put("http://localhost:" + port + "/student/" + newStudent.getId(), updateStudent, Student.class);

//        ResponseEntity<String> response =
//                rest.exchange(url, HttpMethod.PUT, requestEntity, MyObject.class);
    }

    @Test
    public void testDeleteStudentById() throws Exception {
        Student testStudent = new Student("test student", 30);
        ResponseEntity<Student> newStudentResponse =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", testStudent, Student.class);

        Assertions.assertThat(newStudentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student newStudent = newStudentResponse.getBody();

        ResponseEntity<Void> studentEntity =
                testRestTemplate.exchange("http://localhost:" + port + "/student/" + newStudent.getId(),
                        HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        Assertions.assertThat(studentEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(studentEntity.getBody()).isNull();

//exchange(url, HttpMethod.DELETE, new HttpEntity<>(""), String.class)
    }

}