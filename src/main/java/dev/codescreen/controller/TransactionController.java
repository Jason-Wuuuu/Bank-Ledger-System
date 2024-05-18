package dev.codescreen.controller;

import dev.codescreen.model.*;
import dev.codescreen.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping("/authorization")
    public ResponseEntity<AuthorizationResponse> authorize(@Valid @RequestBody AuthorizationRequest request) {
        return ResponseEntity.status(201).body(transactionService.authorize(request));
    }

    @PutMapping("/load")
    public ResponseEntity<LoadResponse> load(@Valid @RequestBody LoadRequest request) {
        return ResponseEntity.status(201).body(transactionService.load(request));
    }
}
