package ru.hogwarts.school.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.StatisticService;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Stream;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/names-student-start-a")
    public List<String> getStudentsNameStartWithA() {
        return statisticService.getStudentsNameStartWithA();
    }

    @GetMapping("/avg-age")
    public OptionalDouble getAvgAgeOfStudents() {
        return statisticService.getAvgAgeOfStudents();
    }

    @GetMapping("/longest-faculty-name")
    public Optional<String> getLongestFacultyName() {
        return statisticService.getLongestFacultyName();
    }

    @GetMapping("/number-parallel")
    public Integer number() {
        return Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
    }
}