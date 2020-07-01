package ru.otus.homework;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.homework.service.PostService;

@Component
@RequiredArgsConstructor
public class PostsHealthIndicator implements HealthIndicator {

    private static final int AVERAGE_POST_COUNT_ON_BOOK = 1;

    private final PostService postService;

    @Override
    public Health health() {

        if (!checkAveragePostCount()) {
            return Health.down().withDetail("Message", "Low activity").build();
        }
        return Health.up().build();
    }

    private boolean checkAveragePostCount() {
        Long averagePostCount = postService.getAveragePostCountOnBook();
        return averagePostCount == null || averagePostCount > AVERAGE_POST_COUNT_ON_BOOK;
    }
}
