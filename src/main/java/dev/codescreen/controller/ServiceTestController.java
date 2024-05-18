package dev.codescreen.controller;

import dev.codescreen.model.Error;
import dev.codescreen.model.Ping;
import dev.codescreen.service.ServiceTestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceTestController {
    private final ServiceTestService serviceTestService;

    public ServiceTestController(ServiceTestService serviceTestService) {
        this.serviceTestService = serviceTestService;
    }

    @GetMapping("/ping")
    public ResponseEntity<Ping> ping() {
        /*
          /ping:
            get:
              summary: >-
                Tests the availability of the service and returns the current server
                time.
              responses:
                200: Ping
                default: ServerError

            ServerError:
              description: Server Error response
              content:
                application/json:
                  schema: Error
         */

        return ResponseEntity.ok(serviceTestService.ping());

    }
}
