package com.demo.api.service.impl;

import com.demo.api.entity.Server;
import com.demo.api.repository.ServerRepository;
import com.demo.api.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.demo.api.enumeration.Status.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);

        // Lấy thông tin địa chỉ mạng từ tên miền hoặc địa chỉ IP được cung cấp.
        // InetAddress address = InetAddress.getByName(ipAddress);

        // Kiểm tra xem địa chỉ có thể được truy cập trong vòng 10 giây (10000 milliseconds).
        // Nếu địa chỉ khả dụng, đặt trạng thái máy chủ là SERVER_UP, nếu không thì đặt là SERVER_DOWN.
        server.setStatus(isReachable(10000) ? SERVER_UP : SERVER_DOWN);

        serverRepository.save(server);
        return server;
    }


    @Override
    public Collection<Server> getServers(int limit) {
        log.info("Fetching all server");
        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server getServer(Long id) {
        log.info("Fetching all server by id: {}", id);
        return serverRepository.findById(id).orElse(null);
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public boolean deleteById(Long id) {
        log.info("Deleting server by id: {}", id);
        serverRepository.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"Server1.png", "Server2.png", "Server3.png", "Server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
    }

    private boolean isReachable(int milliseconds) {
        int millisecondsConnect = new Random().nextInt(2000, milliseconds + 2000);
        try {
            Thread.sleep(millisecondsConnect);
            return millisecondsConnect <= 10000;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }
}
