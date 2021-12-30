package com.fintech.transaction.controller;

import com.fintech.transaction.dto.ErrorResponse;
import com.fintech.transaction.dto.TransactionDto;
import com.fintech.transaction.dto.TransactionResponse;
import com.fintech.transaction.model.Account;
import com.fintech.transaction.model.Transaction;
import com.fintech.transaction.model.TransactionStatus;
import com.fintech.transaction.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * @author: Nathan
 */
@ApiOperation(value = "/transaction/vi/deposit", tags = "Transaction Controller")
@Slf4j
@RestController
@RequestMapping("/fintech/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "deposit", response = TransactionResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = TransactionResponse.class),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = TransactionResponse.class),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> postDeposit(@RequestBody TransactionDto transactionDto){
        Optional<String> depositorDetails = transactionService.getAccount(transactionDto.getAccountNo());
        TransactionResponse transactionResponse = new TransactionResponse();
        if(!depositorDetails.isEmpty()){
            String[] depositorDetailsArray = depositorDetails.get().split(",");
            if(transactionDto.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                Transaction transaction = new Transaction();
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
                transactionService.deposit(transaction);
                transactionService.updateBalance(newBalance, Long.parseLong(depositorDetailsArray[1]),
                        Long.parseLong(depositorDetailsArray[0]));
                transactionResponse = new TransactionResponse(ref,transactionDto.getAmount(),"SUCCESS",
                transactionDto.getTransactionRemark(), transactionDto.getTransactionType(),
                        TransactionStatus.SUCCESS);
            }else {
                log.info("Amount is invalid");
                transactionResponse.setMessage("invalid amount");
                transactionResponse.setTransactionStatus(TransactionStatus.FAILED);
                return new ResponseEntity<>(transactionResponse, HttpStatus.BAD_REQUEST);
            }
        }else {
            log.info("invalid account");
            transactionResponse.setMessage("invalid account");
            transactionResponse.setTransactionStatus(TransactionStatus.FAILED);
            return new ResponseEntity<>(transactionResponse, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(transactionResponse, HttpStatus.CREATED);
    }

    @ApiOperation(value = "getTransaction/ref", response = Transaction.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = Transaction.class),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @RequestMapping("/getTransaction/{ref}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable int ref) {
        Optional<Transaction> transaction;
        try{
            transaction = transactionService.findTransactionByRef(ref);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ResponseEntity<>(new ErrorResponse(200, "01",
                    "No result for specified ID"), HttpStatus.BAD_GATEWAY);
        }
        transaction.ifPresentOrElse(
                o -> log.info("getTransaction {}: {}", ref, o),
                () -> log.info("getTransaction {}: null", ref));
        if(transaction.isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(200, "01",
                    "No result for specified ID"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }

    }
}
