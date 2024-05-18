**Important: Don't forget to update the [Candidate README](#candidate-readme) section**

# Real-time Transaction Challenge

## Overview

Welcome to Current's take-home technical assessment for backend engineers! We appreciate you taking the time to complete this, and we're excited to see what you come up with.

You are tasked with building a simple bank ledger system that utilizes the [event sourcing](https://martinfowler.com/eaaDev/EventSourcing.html) pattern to maintain a transaction history. The system should allow users to perform basic banking operations such as depositing funds, withdrawing funds, and checking balances. The ledger should maintain a complete and immutable record of all transactions, enabling auditability and reconstruction of account balances at any point in time.

## Details

The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service we would like you to create and host.

The service accepts two types of transactions:

1. Loads: Add money to a user (credit)

2. Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT should return the updated balance following the transaction. Authorization declines should be saved, even if they do not impact balance calculation.

Implement the event sourcing pattern to record all banking transactions as immutable events. Each event should capture relevant information such as transaction type, amount, timestamp, and account identifier.
Define the structure of events and ensure they can be easily serialized and persisted to a data store of your choice. We do not expect you to use a persistent store (you can you in-memory object), but you can if you want. We should be able to bootstrap your project locally to test.

## Expectations

We are looking for attention in the following areas:

1. Do you accept all requests supported by the schema, in the format described?

2. Do your responses conform to the prescribed schema?

3. Does the authorizations endpoint work as documented in the schema?

4. Do you have unit and integrations test on the functionality?

Here’s a breakdown of the key criteria we’ll be considering when grading your submission:

**Adherence to Design Patterns:** We’ll evaluate whether your implementation follows established design patterns such as following the event sourcing model.

**Correctness**: We’ll assess whether your implementation effectively implements the desired pattern and meets the specified requirements.

**Testing:** We’ll assess the comprehensiveness and effectiveness of your test suite, including unit tests, integration tests, and possibly end-to-end tests. Your tests should cover critical functionalities, edge cases, and potential failure scenarios to ensure the stability of the system.

**Documentation and Clarity:** We’ll assess the clarity of your documentation, including comments within the code, README files, architectural diagrams, and explanations of design decisions. Your documentation should provide sufficient context for reviewers to understand the problem, solution, and implementation details.

# Candidate README

## Bootstrap instructions

#### To run this server locally, follow these steps:

1. Clone the repository:

   ```bash
   git clone "https://github.com/codescreen/CodeScreen_pzcph7oe.git"
   cd "CodeScreen_pzcph7oe"

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

## Design considerations

### Choice of Spring Boot:

For the backend of our bank ledger system,
I chose Spring Boot because of its robust framework that simplifies the development, testing, and deployment of REST APIs.
The following are some specific reasons and areas where I find Spring Boot is suitable for this assessment:

1. **Ease of Use:** Spring Boot allows for quick setup and minimal configuration, which helps in focusing more on business logic rather than boilerplate code.
2. **Rich Ecosystem:** It integrates well with various other technologies and tools, making it versatile for future enhancements.
3. **Scalability:** It supports scalable solutions, essential for managing the potential high loads in banking applications.

#### Key Spring Boot Dependencies I've chosen to use with reasons:

- **spring-boot-starter-web**: Provides essential tools for building RESTful applications, including REST controller support and servlet integrations.
- **spring-boot-starter-validation**: Ensures that all input data to the API meets the expected formats and constraints, crucial for maintaining data integrity and security.
- **spring-boot-starter-test**: Offers comprehensive testing capabilities, integrating seamlessly with JUnit to allow for effective testing practices that cover unit and integration tests.

#### My Spring Boot Structure:

- `/model`: Contains domain models representing data structures for Amount, TransactionRequests, TransactionResponse, TransactionEvents, Error and enums.
- `/repository`: Handles the simulation of an in-memory data store for event sourcing, ensuring all events are stored and retrieved efficiently.
- `/controller`: Manages all the API endpoints, handling incoming requests and delegating operations to services.
- `/service`: Implements the business logic of the banking operations, ensuring the correct application of the event sourcing model.

### Choice of Guava for implementing Event Sourcing:

For event sourcing, I utilized Google's Guava library, specifically its ImmutableList, to meet the event sourcing requirements.
ImmutableList ensures that transaction events are immutable, preserving the consistency and traceability necessary for reliable auditing and state reconstruction.

#### Event Sourcing Components:

- **TransactionEvent.java**: Defines the structure of the transaction event.
- **InMemoryEventStore.java**: Simulates an in-memory event store, providing methods to append new events and retrieve existing ones without the risk of data modification.
- **BalanceProjection.java**: Computes the current balance from the sequence of past events, demonstrating the ability to reconstruct the state from events, which is a central principle of event sourcing.

#### Usage of BigDecimal and synchronized:

- **BigDecimal (BalanceProjection.java):** I choose BigDecimal for all monetary calculations to ensure precision and prevent rounding errors that can occur with float or double data types. I believe this is essential in financial applications where exact decimal values are necessary for accurate accounting and financial reporting.
- **synchronized (InMemoryEventStore.java):** The synchronized keyword is used to ensure that modifications to the balance are thread-safe, meaning that one thread's changes to a user's balance are visible to all other threads. This prevents race conditions and ensures that the service remains correct and reliable under concurrent access.

### Choice of JUnit and Spring Boot Test for testing:

For testing, I utilize JUnit in conjunction with Spring Boot Test.
This combination provides a robust framework for validating both the individual components and the integrated behavior of the application.

**Main Testing Approach:**
The core of my testing strategy involves simulating real API interactions based on predefined scenarios.
I loop through entries in the sample_test file, invoking the API according to the specified transaction type (LOAD or AUTHORIZATION).
Each call includes the appropriate request body. The response body is then captured and compared against the expected results, ensuring that the API behaves as intended under various conditions.

## Assumptions

1. Authorizations consistently involve debiting funds, while Loads exclusively pertain to crediting funds.
2. Loads are automatically approved; authorizations are declined only if the balance is insufficient.
3. I am allowed to modify the sample_test to correct inconsistencies, such as missing fields and incorrect terminology (e.g., changing "DENIED" to "DECLINED").

## Bonus: Deployment considerations

If I were to deploy these, I would consider a containerized approach using Docker for consistency across environments, orchestrated with Kubernetes for scalability.
This setup would be hosted on a cloud platform like AWS, utilizing services like Elastic Beanstalk for easy application management and scaling, and RDS for managed database services.
This would ensure a robust, scalable, and maintainable system.

## License

At CodeScreen, we strongly value the integrity and privacy of our assessments. As a result, this repository is under exclusive copyright, which means you **do not** have permission to share your solution to this test publicly (i.e., inside a public GitHub/GitLab repo, on Reddit, etc.). <br>

## Submitting your solution

Please push your changes to the `main branch` of this repository. You can push one or more commits. <br>

Once you are finished with the task, please click the `Submit Solution` link on <a href="https://app.codescreen.com/candidate/5a195f58-92e2-4f41-b6cb-e56ea1fcd585" target="_blank">this screen</a>.
