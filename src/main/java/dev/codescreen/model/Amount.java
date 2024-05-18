package dev.codescreen.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/*
    Amount:
      type: object
      additionalProperties: false
      required:
        - amount
        - currency
        - debitOrCredit
      properties:
        amount:
          type: string
          description: The amount in the denomination of the currency. For example, $1 = '1.00'
          minLength: 1
        currency:
          type: string
          minLength: 1
        debitOrCredit: DebitCredit
 */

public class Amount {

    @NotBlank(message = "Amount cannot be blank")
    @Size(min = 1, message = "Amount must have at least one character")
    private String amount;

    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 1, message = "Amount must have at least one character")
    private String currency;

    @NotNull(message = "DebitOrCredit cannot be null")
    private DebitCredit debitOrCredit;

    /**
     * Constructor for the Amount class.
     * Initializes the amount, currency, and debit or credit status of the transaction.
     *
     * @param amount        the monetary amount
     * @param currency      the currency of the amount
     * @param debitOrCredit the transaction type, either DEBIT or CREDIT
     */
    public Amount(String amount, String currency, DebitCredit debitOrCredit) {
        this.amount = amount;
        this.currency = currency;
        this.debitOrCredit = debitOrCredit;
    }

    // Getters and Setters
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public DebitCredit getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(DebitCredit debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", debitOrCredit=" + debitOrCredit +
                '}';
    }
}
