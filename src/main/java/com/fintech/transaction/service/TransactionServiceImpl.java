package com.fintech.transaction.service;

import com.fintech.transaction.model.Account;
import com.fintech.transaction.model.Transaction;
import com.fintech.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author: Nathan
 */
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(){}

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository){this.transactionRepository = transactionRepository;}

    @Override
    public void deposit(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public Optional<String> getAccount(long accountNo) {
        log.info("validating account: "+accountNo);
        return transactionRepository.findByAccount(accountNo);
    }

    @Override
    public void updateBalance(BigDecimal balance, long accountNo, long customerid) {
        transactionRepository.updateBalance(balance, accountNo, customerid);
    }

    @Override
    public Optional<Transaction> findTransactionByRef(long ref) {
        log.info("finding ref: "+ref);
        return transactionRepository.findTransactionByRef(ref);
    }
}
