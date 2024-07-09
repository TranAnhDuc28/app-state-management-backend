package com.demo.api.controller;

import com.demo.api.dto.common.Response;
import com.demo.api.entity.Server;
import com.demo.api.service.ServerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.demo.api.enumeration.Status.*;
import static java.time.LocalDateTime.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/server")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @GetMapping("/list")
    private ResponseEntity<Response> getServers() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3); // tạm dừng việc thực thi của luồng hiện tại trong 3 giây, tương tự Thread.sleep(3000)
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message("Servers retrieved")  // retrieved: đã được lấy ra
                        .status(OK)
                        .statusCode(OK.value())
                        .data(Map.of("servers", serverService.getServers(30)))
                        .build());
    }

    @GetMapping("/ping/{ipAddress}")
    private ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message(server.getStatus() == SERVER_UP ? "Ping success": "Ping failed")  // retrieved: đã được lấy ra
                        .status(OK)
                        .statusCode(OK.value())
                        .data(Map.of("server", server))
                        .build());
    }

    @PostMapping("/save")
    private ResponseEntity<Response> saveServer(@Valid @RequestBody Server server) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message("Server created")  // retrieved: đã được lấy ra
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .data(Map.of("server", serverService.create(server)))
                        .build());
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Response> saveServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message("Server deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(Map.of("deleted", serverService.deleteById(id)))
                        .build());
    }

    @GetMapping("/get/{id}")
    private ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        Server server = serverService.getServer(id);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message("Server retrieved")  // retrieved: đã được lấy ra
                        .status(OK)
                        .statusCode(OK.value())
                        .data(Map.of("server", server))
                        .build());
    }


    /**
     * produces: loại nội dung của phản hồi
     * MediaType.IMAGE_PNG_VALUE: phản hồi sẽ là một hình ảnh PNG. Đây là một giá trị chuỗi "image/png".
     * @param fileName
     * @return
     */
    @GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    private byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources/static/images/" + fileName).toAbsolutePath());
    }
}
