package dev.codescreen.service;

import dev.codescreen.model.Error;
import dev.codescreen.model.Ping;
import org.springframework.stereotype.Service;

@Service
public class ServiceTestService {
    public Ping ping() {
        return new Ping(java.time.LocalDateTime.now().toString());
    }
}
