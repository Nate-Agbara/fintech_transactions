package com.fintech.transaction.service;

import com.fintech.transaction.model.Account;
import com.fintech.transaction.model.Transaction;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author: Nathan
 */
public interface TransactionService {

    void deposit(Transaction transaction);

    Optional<String> getAccount(long accountNo);

    void updateBalance(BigDecimal balance, long accountNo, long customerid);

    Optional<Transaction> findTransactionByRef(long ref);
}
