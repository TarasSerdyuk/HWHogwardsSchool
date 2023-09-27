package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.HamcrestCondition;
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
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.model.Faculty;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class FacultyControllerRestTemplateIntegrationTest{
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception{
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testPostFaculty() throws Exception {
        Faculty testFaculty = new Faculty("name", "color");

        ResponseEntity<Faculty> newFacultyResponse =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", testFaculty, Faculty.class);

        Assertions.assertThat(newFacultyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(newFacultyResponse.getBody()).isNotNull();
    }

    @Test
    public void testGetFaculties() throws Exception {
        Faculty testFaculty = new Faculty("name", "color");

        ResponseEntity<Faculty> newFacultyResponse =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", testFaculty, Faculty.class);

        Assertions.assertThat(newFacultyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Faculty newFaculty = newFacultyResponse.getBody();

        ResponseEntity<Faculty[]> facultyEntity =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty", Faculty[].class);

        Assertions.assertThat(facultyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(facultyEntity.getBody()).isNotNull();
        Assertions.assertThat(facultyEntity.getBody()).contains(newFaculty);
    }

    @Test
    public void testGetFacultyById() throws Exception {
        Faculty testFaculty = new Faculty("name", "color");

        ResponseEntity<Faculty> newFacultyResponse =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", testFaculty, Faculty.class);

        Assertions.assertThat(newFacultyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Faculty newFaculty = newFacultyResponse.getBody();

        ResponseEntity<Faculty> facultyEntity =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + newFaculty.getId(), Faculty.class);

        Assertions.assertThat(facultyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(facultyEntity.getBody()).isNotNull();
        Assertions.assertThat(facultyEntity.getBody().getId()).isEqualTo(newFaculty.getId());
        Assertions.assertThat(facultyEntity.getBody().getName()).isEqualTo(newFaculty.getName());
        Assertions.assertThat(facultyEntity.getBody().getColor()).isEqualTo(newFaculty.getColor());
    }

    @Test
    public void testGetFacultiesByName() throws Exception {
        Faculty testFaculty1 = new Faculty("name", "red");
        Faculty testFaculty2 = new Faculty("name2", "white");

        String name = "name";

        ResponseEntity<Faculty> newFacultyResponse1 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", testFaculty1, Faculty.class);
        ResponseEntity<Faculty> newFacultyResponse2 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", testFaculty2, Faculty.class);

        Assertions.assertThat(newFacultyResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(newFacultyResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);

        Faculty newFaculty1 = newFacultyResponse1.getBody();
        Faculty newFaculty2 = newFacultyResponse2.getBody();

        ResponseEntity<Faculty[]> facultyEntity =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty?name=" + name, Faculty[].class);

        Assertions.assertThat(facultyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(facultyEntity.getBody()).isNotNull();
        Assertions.assertThat(facultyEntity.getBody()).contains(newFaculty1);
    }

    @Test
    public void testGetFacultiesByColor() throws Exception {
        Faculty testFaculty1 = new Faculty("name", "white");
        Faculty testFaculty2 = new Faculty("name2", "white");
        Faculty testFaculty3 = new Faculty("name", "pink");

        String color = "white";

        ResponseEntity<Faculty> newFacultyResponse1 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", testFaculty1, Faculty.class);
        ResponseEntity<Faculty> newFacultyResponse2 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", testFaculty2, Faculty.class);
        ResponseEntity<Faculty> newFacultyResponse3 =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", testFaculty3, Faculty.class);

        Assertions.assertThat(newFacultyResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(newFacultyResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(newFacultyResponse3.getStatusCode()).isEqualTo(HttpStatus.OK);

        Faculty newFaculty1 = newFacultyResponse1.getBody();
        Faculty newFaculty2 = newFacultyResponse2.getBody();
        Faculty newFaculty3 = newFacultyResponse3.getBody();

        ResponseEntity<Faculty[]> facultyEntity =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty?color=" + color, Faculty[].class);

        Assertions.assertThat(facultyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(facultyEntity.getBody()).isNotNull();
        Assertions.assertThat(facultyEntity.getBody()).contains(newFaculty1);
        Assertions.assertThat(facultyEntity.getBody()).contains(newFaculty2);
        Assertions.assertThat(facultyEntity.getBody()).doesNotContain(newFaculty3);
    }

    @Test
    public void testPutFacultyById() throws Exception {
        Faculty testFaculty = new Faculty("name", "color");

        ResponseEntity<Faculty> newFacultyResponse =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", testFaculty, Faculty.class);

        Assertions.assertThat(newFacultyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Faculty newFaculty = newFacultyResponse.getBody();
        newFaculty.setColor("new color");
        newFaculty.setName("new name");

        HttpEntity<Faculty> newFacultyEntity = new HttpEntity<>(newFaculty, newFacultyResponse.getHeaders());

        ResponseEntity<Faculty> facultyEntity =
                testRestTemplate.exchange("http://localhost:" + port + "/faculty/" + newFaculty.getId(),
                        HttpMethod.PUT, newFacultyEntity, Faculty.class);

        Assertions.assertThat(facultyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(facultyEntity.getBody()).isNotNull();
        Assertions.assertThat(facultyEntity.getBody().getId()).isEqualTo(newFaculty.getId());
        Assertions.assertThat(facultyEntity.getBody().getName()).isEqualTo(newFaculty.getName());
        Assertions.assertThat(facultyEntity.getBody().getColor()).isEqualTo(newFaculty.getColor());
    }
    @Test
    public void testDeleteFacultyById() throws Exception {
        Faculty testFaculty = new Faculty("name", "color");

        ResponseEntity<Faculty> newFacultyResponse =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", testFaculty, Faculty.class);

        Assertions.assertThat(newFacultyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Faculty newFaculty = newFacultyResponse.getBody();

        ResponseEntity<Void> facultyEntity =
                testRestTemplate.exchange("http://localhost:" + port + "/faculty/" + newFaculty.getId(),
                        HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        Assertions.assertThat(facultyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(facultyEntity.getBody()).isNull();
    }
}