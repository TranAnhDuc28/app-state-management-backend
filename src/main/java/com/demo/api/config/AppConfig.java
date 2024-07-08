package com.demo.api.config;

import com.demo.api.entity.Server;
import com.demo.api.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static com.demo.api.enumeration.Status.SERVER_DOWN;
import static com.demo.api.enumeration.Status.SERVER_UP;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner run(ServerRepository serverRepository) {
        return args -> {
            serverRepository.save(new Server(null, "192.168.123.7", "Wi-Fi", "16 GB",
                    "Personal PC", "http://localhost:8080/api/server/image/server1.png", SERVER_UP));
            serverRepository.save(new Server(null, "172.23.32.1", "Ubuntu Linux", "16 GB",
                    "Personal PC", "http://localhost:8080/api/server/image/server2.png", SERVER_UP));
            serverRepository.save(new Server(null, "192.168.123.8", "Ethernet", "8 GB",
                    "Workstation", "http://localhost:8080/api/server/image/server3.png", SERVER_DOWN));
            serverRepository.save(new Server(null, "10.0.0.1", "Windows Server", "32 GB",
                    "Server Room", "http://localhost:8080/api/server/image/server4.png", SERVER_UP));
            serverRepository.save(new Server(null, "192.168.0.10", "Mac OS", "16 GB",
                    "Development Machine", "http://localhost:8080/api/server/image/server1.png", SERVER_DOWN));
            serverRepository.save(new Server(null, "172.16.0.5", "CentOS", "64 GB",
                    "Database Server", "http://localhost:8080/api/server/image/server2.png", SERVER_UP));
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
