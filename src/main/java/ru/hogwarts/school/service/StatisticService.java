package ru.hogwarts.school.service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public interface StatisticService {
    List<String> getStudentsNameStartWithA();

    OptionalDouble getAvgAgeOfStudents();

    Optional<String> getLongestFacultyName();
}