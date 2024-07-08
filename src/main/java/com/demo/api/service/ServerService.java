package com.demo.api.service;

import com.demo.api.entity.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {

    Server create(Server server);
    Server ping(String ipAddress) throws IOException;
    Collection<Server> getServers(int limit);
    Server getServer(Long id);
    Server update(Server server);
    boolean deleteById(Long id);

}
