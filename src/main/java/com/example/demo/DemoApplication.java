package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {

    @Test
    void contextLoads() {
        // Tämä testi menee läpi, jos Spring Boot saa sovelluksen ja tietokantayhteyden käyntiin onnistuneesti
    }
}