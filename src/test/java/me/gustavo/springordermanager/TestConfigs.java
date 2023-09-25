package me.gustavo.springordermanager;

import org.flywaydb.core.Flyway;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfigs {

    @TestConfiguration
    public class TestFlywayConfig {
        @Bean
        @Primary
        public Flyway flyway() {
            Flyway flyway = Flyway.configure()
                    .locations("classpath:db/migration", "classpath:db/migration/test")
                    .dataSource(dataSource())
                    .load();
            flyway.migrate();
            return flyway;
        }

        @Bean
        public DataSource dataSource() {
            // Configure a test-specific data source here
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl("jdbc:h2:mem:testdb");
            dataSource.setUsername("sa");
            dataSource.setPassword("password");
            return dataSource;
        }
    }

}
