package dev.codescreen.model;

/*
    AuthorizationResponse:
      type: object
      additionalProperties: false
      description: The result of an authorization
      required:
        - userId
        - messageId
        - responseCode
        - balance
      properties:
        userId:
          type: string
          minLength: 1
        messageId:
          type: string
          minLength: 1
        responseCode: ResponseCode
        balance: Amount
 */

public class AuthorizationResponse extends TransactionResponse{
    private ResponseCode responseCode;

    // Constructors
    public AuthorizationResponse(String userId, String messageId, ResponseCode responseCode, Amount balance) {
        super(userId, messageId, balance);
        this.responseCode = responseCode;
    }

    // Getters and Setters
    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

}
