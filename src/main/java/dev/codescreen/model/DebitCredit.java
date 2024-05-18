package dev.codescreen.model;

/*
    DebitCredit:
      type: string
      description: >-
        Debit or Credit flag for the network transaction. A Debit deducts funds from a user. A credit adds funds to a user.
      enum:
        - DEBIT
        - CREDIT
 */

public enum DebitCredit {
    DEBIT, CREDIT
}
