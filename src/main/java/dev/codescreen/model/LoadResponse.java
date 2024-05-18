package dev.codescreen.model;

/*
    LoadResponse:
      type: object
      additionalProperties: false
      description: The result of a load.
      required:
        - userId
        - messageId
        - balance
      properties:
        userId:
          type: string
          minLength: 1
        messageId:
          type: string
          minLength: 1
        balance: Amount
 */

public class LoadResponse extends TransactionResponse{

    // Constructors
    public LoadResponse(String userId, String messageId, Amount balance) {
        super(userId, messageId, balance);
    }
}
