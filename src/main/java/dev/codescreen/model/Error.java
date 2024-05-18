package dev.codescreen.model;

/*
    Error:
      type: object
      additionalProperties: false
      required:
        - message
      description: Error message
      properties:
        message:
          type: string
          minLength: 1
        code:
          type: string
          minLength: 1
 */

public class Error {
    private String message;
    private String code;

    // Constructors
    public Error(String message) {
        this.message = message;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
