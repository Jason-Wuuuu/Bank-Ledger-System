package dev.codescreen.model;

/*
    AuthorizationRequest:
      type: object
      additionalProperties: false
      required:
        - userId
        - messageId
        - transactionAmount
      description: Authorization request that needs to be processed.
      properties:
        userId:
          type: string
          minLength: 1
        messageId:
          type: string
          minLength: 1
        transactionAmount: Amount
 */

public class AuthorizationRequest extends TransactionRequest {

    // Constructors
    public AuthorizationRequest(String userId, String messageId, Amount transactionAmount) {
        super(userId, messageId, transactionAmount);
    }
}
