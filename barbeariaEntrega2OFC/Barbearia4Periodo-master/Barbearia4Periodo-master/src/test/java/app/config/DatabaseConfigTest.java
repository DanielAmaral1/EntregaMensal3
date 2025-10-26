package app.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig
class DatabaseConfigTest {

    @Test
    void testDatabaseConfigCreation() {
        DatabaseConfig config = new DatabaseConfig();
        assertNotNull(config);
    }

    @Test
    void testDatabaseConfigIsConfiguration() {
        DatabaseConfig config = new DatabaseConfig();
        assertTrue(config.getClass().isAnnotationPresent(org.springframework.context.annotation.Configuration.class));
    }
}