package dev.codescreen.service;

import dev.codescreen.model.*;
import dev.codescreen.model.Error;

public interface TransactionService {
    AuthorizationResponse authorize(AuthorizationRequest request);
    LoadResponse load(LoadRequest request);
}
