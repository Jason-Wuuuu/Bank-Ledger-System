package dev.codescreen.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.codescreen.exeption.EventStoreException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import dev.codescreen.model.*;
import dev.codescreen.service.TransactionService;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger =  Logger.getLogger(TransactionControllerTest.class.getName());

    @Test
    public void whenAuthorizationRequestHaveMissingFields_thenReturnsBadRequest() throws Exception {
        List<AuthorizationRequest> invalidRequests = Arrays.asList(
                // Missing messageId
                new AuthorizationRequest("1", "", new Amount("100", "USD", DebitCredit.DEBIT)),
                // Missing userId
                new AuthorizationRequest("", "1", new Amount("100", "USD", DebitCredit.DEBIT)),
                // Missing amount
                new AuthorizationRequest("1", "1", new Amount("", "USD", DebitCredit.DEBIT)),
                // Missing currency
                new AuthorizationRequest("1", "1", new Amount("100", "", DebitCredit.DEBIT)),
                // Missing debitOrCredit
                new AuthorizationRequest("1", "1", new Amount("100", "USD", null))
        );

        for (AuthorizationRequest request : invalidRequests) {
            mockMvc.perform(put("/authorization")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(String.valueOf(HttpStatus.BAD_REQUEST)));
        }
    }

    @Test
    public void whenAuthorizationFailedToStoreEvent_thenReturnsInternalServerError() throws Exception {
        AuthorizationRequest validRequest = new AuthorizationRequest("1", "1", new Amount("100", "USD", DebitCredit.DEBIT));
        when(transactionService.authorize(any(AuthorizationRequest.class)))
                .thenThrow(new EventStoreException("Failed to store event"));

        mockMvc.perform(put("/authorization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Failed to store event"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    public void whenLoadRequestHaveMissingFields_thenReturnsBadRequest() throws Exception {
        List<LoadRequest> invalidRequests = Arrays.asList(
                // Missing messageId
                new LoadRequest("1", "", new Amount("100", "USD", DebitCredit.DEBIT)),
                // Missing userId
                new LoadRequest("", "1", new Amount("100", "USD", DebitCredit.DEBIT)),
                // Missing amount
                new LoadRequest("1", "1", new Amount("", "USD", DebitCredit.DEBIT)),
                // Missing currency
                new LoadRequest("1", "1", new Amount("100", "", DebitCredit.DEBIT)),
                // Missing debitOrCredit
                new LoadRequest("1", "1", new Amount("100", "USD", null))
        );

        for (LoadRequest request : invalidRequests) {
            mockMvc.perform(put("/authorization")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(String.valueOf(HttpStatus.BAD_REQUEST)));
        }
    }

    @Test
    public void whenLoadFailedToStoreEvent_thenReturnsInternalServerError() throws Exception {
        AuthorizationRequest validRequest = new AuthorizationRequest("1", "1", new Amount("100", "USD", DebitCredit.DEBIT));
        when(transactionService.authorize(any(AuthorizationRequest.class)))
                .thenThrow(new EventStoreException("Failed to store event"));

        mockMvc.perform(put("/authorization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Failed to store event"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    public void runTransactionSampleTestsForEventSourcingLogics() throws Exception {
        // read the sample_tests file
        String path = new ClassPathResource("sample_tests").getFile().getPath();

        // go through every line
        try (Stream<String> stream = Files.lines(Paths.get(path)).skip(1)) { // Skip the first line
            stream.forEach(line -> {
                try {
                    processLine(line);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error processing line: " + line, e);
                }
            });
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file: " + path, e);
        }
    }

    private void processLine(String line) throws Exception {
        String[] parts = line.split(",");

        String action = parts[0];
        String messageId = parts[1];
        String userId = parts[2];
        String debitCredit = parts[3];
        String amount = parts[4];
        String responseCode = parts[5];
        String balance = parts[6];

        Amount transactionAmount = new Amount(amount, "USD", DebitCredit.valueOf(debitCredit));
        Amount balanceAmount = new Amount(balance, "USD", DebitCredit.valueOf(debitCredit));

        if ("LOAD".equals(action)) {
            // load
            LoadRequest request = new LoadRequest(userId, messageId, transactionAmount);
            LoadResponse loadResponse = new LoadResponse(userId, messageId, balanceAmount);

            when(transactionService.load(any(LoadRequest.class))).thenReturn(loadResponse);

            String jsonRequest = objectMapper.writeValueAsString(request);
            // System.out.println(jsonRequest);

            String jsonResponse = objectMapper.writeValueAsString(loadResponse);
            // System.out.println(jsonResponse);

            mockMvc.perform(put("/load")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonRequest))
                    // .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(jsonResponse)); // check if response matches// authentication
        } else if ("AUTHORIZATION".equals(action)) {
            // authentication
            AuthorizationRequest request = new AuthorizationRequest(userId, messageId, transactionAmount);
            AuthorizationResponse authResponse = new AuthorizationResponse(userId, messageId, ResponseCode.valueOf(responseCode), balanceAmount);

            when(transactionService.authorize(any(AuthorizationRequest.class))).thenReturn(authResponse);

            String jsonRequest = objectMapper.writeValueAsString(request);
            // System.out.println(request);

            String jsonResponse = objectMapper.writeValueAsString(authResponse);
            // System.out.println(jsonResponse);

            mockMvc.perform(put("/authorization")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonRequest))
                    // .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(jsonResponse));
        }
    }
}

