package com.example.demo;


import lombok.extern.slf4j.Slf4j;
import ch.qos.logback.classic.Logger;
 
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
 
@Configuration
@Slf4j
class LoadDatabase {
 
    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            Logger log = (Logger) LoggerFactory.getLogger(Employee.class);
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
        };
    }
}