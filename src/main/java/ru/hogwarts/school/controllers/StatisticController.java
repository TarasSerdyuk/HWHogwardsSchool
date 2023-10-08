package ru.hogwarts.school.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.StatisticService;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.lang.*;

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

        public void number() {
        long start = System.currentTimeMillis();
        Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
            long duration = System.currentTimeMillis() - start;
            System.out.println("Время выполнения кода паралельно: " + duration + " мс.");
        start = System.currentTimeMillis();
        //2 вариант
        Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        duration = System.currentTimeMillis() - start;
        System.out.println("Время выполнения кода вариант 2 паралельно: " + duration + " мс.");
        // 3 вариант
        start = System.currentTimeMillis();
        Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        duration = System.currentTimeMillis() - start;
        System.out.println("Время выполнения кода вариант 3: " + duration + " мс.");
        // 4 вариант
        start = System.currentTimeMillis();
        Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
        duration = System.currentTimeMillis() - start;
        System.out.println("Время выполнения кода вариант 4: " + duration + " мс.");
        // 5 вариант
        start = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1_000_000)
                .reduce(0, Integer::sum);
        duration = System.currentTimeMillis() - start;
        System.out.println("Время выполнения кода вариант 5: " + duration + " мс.");

    }
}