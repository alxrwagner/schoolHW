package ru.hogwarts.schoolHW;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.hogwarts.schoolHW.config.TestConfig;

@SpringBootTest
@Testcontainers
class SchoolHwApplicationTests extends TestConfig {

	@Test
	void contextLoads() {
	}

}
