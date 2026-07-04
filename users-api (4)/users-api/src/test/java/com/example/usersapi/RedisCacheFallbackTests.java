package com.example.usersapi;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.example.usersapi.dto.UserCreateRequest;
import com.example.usersapi.model.User;
import com.example.usersapi.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.cache.type=redis",
        "spring.redis.host=127.0.0.1",
        "spring.redis.port=6399"
})
class RedisCacheFallbackTests {

    @Autowired
    private UserService userService;

    @Test
    void findAllStillWorksWhenRedisIsUnavailable() {
        UserCreateRequest request = new UserCreateRequest();
        request.setName("RedisFallback");
        request.setEmail("redisfallback@example.com");
        request.setPassword("password123");
        request.setAge(30);

        userService.create(request);
        List<User> users = userService.findAll();

        assertThat(users).isNotEmpty();
    }
}
