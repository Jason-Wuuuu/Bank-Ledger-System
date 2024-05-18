package dev.codescreen.model;

/*
    Ping:
      type: object
      additionalProperties: false
      required:
        - serverTime
      properties:
        serverTime:
          type: string
          format: date-time
          description: Current server time
 */

public class Ping {
    // Current server time (date-time)
    private String serverTime;

    // Constructors
    public Ping(String serverTime) {
        this.serverTime = serverTime;
    }

    // Getters and Setters
    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
