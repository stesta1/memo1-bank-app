package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionID;

    private TransactionType type;
    private Long accountCBU;
    private Double transactionAmount;

    public Transaction(){
    }

    public Transaction(TransactionType type, Double transactionAmount, Long accountCBU) {
        this.type = type;
        this.transactionAmount = transactionAmount;
        this.accountCBU = accountCBU;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setAccountCBU(Long accountCBU) {
        this.accountCBU = accountCBU;
    }

    public Long getAccountCBU() {
        return accountCBU;
    }
}
