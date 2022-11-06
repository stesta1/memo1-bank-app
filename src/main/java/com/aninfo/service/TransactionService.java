package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    public Transaction createTransaction(Transaction transaction) {
        TransactionType transactionType = transaction.getType();
        Double transactionAmount = transaction.getTransactionAmount();
        Long accountCBU = transaction.getAccountCBU();
        if (transactionType == TransactionType.WITHDRAW) {
            accountService.withdraw(accountCBU, transactionAmount);
        }
        if (transactionType == TransactionType.DEPOSIT) {
            accountService.deposit(accountCBU, transactionAmount);
        }
        return transactionRepository.save(transaction);
    }



    public Collection<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findById(Long transactionID) {
        return transactionRepository.findById(transactionID);
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void deleteById(Long transactionID) {
        transactionRepository.deleteById(transactionID);
    }

    public Optional<Collection<Transaction>> findByCBU(Long cbu) {
        Collection<Transaction> transactions = transactionRepository.findAll();
        Collection<Transaction> accountTransactions = new ArrayList<Transaction>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccountCBU() == cbu) {
                accountTransactions.add(transaction);
            }
        }
        return Optional.of(accountTransactions);
    }
}
