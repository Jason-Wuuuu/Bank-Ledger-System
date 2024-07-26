## Overview

This is a simple bank ledger system that utilizes the [event sourcing](https://martinfowler.com/eaaDev/EventSourcing.html) pattern to record all banking transactions as immutable events. 
- Each event captures relevant information such as transaction type, amount, timestamp, and account identifier.
- The system allows users to perform basic banking operations such as depositing funds, withdrawing funds, and checking balances.
- The ledger maintains a complete and immutable record of all transactions, enabling auditability and reconstruction of account balances at any point in time.

## Details

The service accepts two types of transactions:

1. Loads: Add money to a user (credit)

2. Authorizations: Conditionally remove money from a user (debit)

## Bootstrap instructions

#### To run this server locally, follow these steps:

1. Clone the repository:

   ```bash
   git clone "https://github.com/Jason-Wuuuu/Bank-Ledger-System.git"
   cd "Bank-Ledger-System"

   ```

2. Build the project:

   ```bash
   mvn clean install

   ```

3. Run the application:

   ```bash
   mvn spring-boot:run

   ```

The API will be accessible via `http://localhost:8080`.

---

#### To test the API endpoints using any HTTP client, such as Postman or cURL:

- To perform a ping test:

  ```bash
  curl -X GET http://localhost:8080/ping
  ```

  > Successful Response :
  >
  > ```json
  > {
  >   "serverTime": "string"
  > }
  > ```
  >
  > Failed Response :
  >
  > ```json
  > {
  >   "message": "string",
  >   "code": "string"
  > }
  > ```

- To perform a load transaction:

  ```bash
  curl -X PUT http://localhost:8080/load -H "Content-Type: application/json" -d '[request body]'

  ```

  > Example request body :
  >
  > ```json
  > {
  >   "messageId": "50e70c62-e480-49fc-bc1b-e991ac672173",
  >   "userId": "8786e2f9-d472-46a8-958f-d659880e723d",
  >   "transactionAmount": {
  >     "amount": "100.23",
  >     "currency": "USD",
  >     "debitOrCredit": "CREDIT"
  >   }
  > }
  > ```
  >
  > Make sure to wrap JSON data string (request body) in single quotes if using cURL

  > Successful Response :
  >
  > ```json
  > {
  >   "userId": "string",
  >   "messageId": "string",
  >   "balance": {
  >     "amount": "string",
  >     "currency": "string",
  >     "debitOrCredit": "string"
  >   }
  > }
  > ```
  >
  > Failed Response :
  >
  > ```json
  > {
  >   "message": "string",
  >   "code": "string"
  > }
  > ```

- To perform an authorization:

  ```bash
  curl -X PUT http://localhost:8080/authorization -H "Content-Type: application/json" -d '[request body]'

  ```

  > Example request body :
  >
  > ```json
  > {
  >   "messageId": "50e70c62-e480-49fc-bc1b-e991ac672173",
  >   "userId": "8786e2f9-d472-46a8-958f-d659880e723d",
  >   "transactionAmount": {
  >     "amount": "9000",
  >     "currency": "USD",
  >     "debitOrCredit": "DEBIT"
  >   }
  > }
  > ```
  >
  > Make sure to wrap JSON data string (request body) in single quotes if using cURL

  > Successful Response :
  >
  > ```json
  > {
  >   "userId": "string",
  >   "messageId": "string",
  >   "balance": {
  >     "amount": "string",
  >     "currency": "string",
  >     "debitOrCredit": "string"
  >   },
  >   "responseCode": "string"
  > }
  > ```
  >
  > Failed Response :
  >
  > ```json
  > {
  >   "message": "string",
  >   "code": "string"
  > }
  > ```
