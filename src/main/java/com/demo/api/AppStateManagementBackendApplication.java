package com.demo.api;

import com.demo.api.entity.Server;
import com.demo.api.enumeration.Status;
import com.demo.api.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.demo.api.enumeration.Status.*;

@SpringBootApplication
public class AppStateManagementBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppStateManagementBackendApplication.class, args);
    }
}
