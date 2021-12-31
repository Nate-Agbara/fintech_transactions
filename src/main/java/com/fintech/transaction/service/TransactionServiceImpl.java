package com.fintech.transaction.service;

import com.fintech.transaction.dto.TransactionDto;
import com.fintech.transaction.dto.TransactionResponse;
import com.fintech.transaction.model.Account;
import com.fintech.transaction.model.Transaction;
import com.fintech.transaction.model.TransactionStatus;
import com.fintech.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

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
    public TransactionResponse deposit(Transaction transaction, TransactionDto transactionDto) {
        Optional<String> depositorDetails = this.getAccount(transactionDto.getAccountNo());
        TransactionResponse transactionResponse = new TransactionResponse();
        if(!depositorDetails.isEmpty()){
            String[] depositorDetailsArray = depositorDetails.get().split(",");
            if(transactionDto.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                //Transaction transaction = new Transaction();
                transaction.setCustomerId(Long.parseLong(depositorDetailsArray[0]));
                transaction.setAccountNo(Long.parseLong(depositorDetailsArray[1]));
                transaction.setTransactionRemark(transactionDto.getTransactionRemark());
                long ref = new Random().nextLong(1000000);
                transaction.setTransactionRef(ref);
                transaction.setTransactionType(transactionDto.getTransactionType());
                transaction.setAmount(transactionDto.getAmount());
                BigDecimal newBalance = BigDecimal.valueOf(Double.parseDouble(depositorDetailsArray[2])).add(transactionDto.getAmount());
                transaction.setBalance(newBalance);
                transaction.setSentEmailNotification(false);
                transaction.setSentSmsNotification(false);
                transaction.setTransactionStatus(TransactionStatus.SUCCESS);
                transactionRepository.save(transaction);
                this.updateBalance(newBalance, Long.parseLong(depositorDetailsArray[1]),
                        Long.parseLong(depositorDetailsArray[0]));
                transactionResponse = new TransactionResponse(ref,transactionDto.getAmount(),"SUCCESS",
                        transactionDto.getTransactionRemark(), transactionDto.getTransactionType(),
                        TransactionStatus.SUCCESS);
                return transactionResponse;
            }else {
                log.info("Amount is invalid");
                transactionResponse.setMessage("invalid amount");
                transactionResponse.setTransactionStatus(TransactionStatus.FAILED);
                return transactionResponse;
            }
        }else {
            log.info("invalid account");
            transactionResponse.setMessage("invalid account");
            transactionResponse.setTransactionStatus(TransactionStatus.FAILED);
            return transactionResponse;
        }

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
