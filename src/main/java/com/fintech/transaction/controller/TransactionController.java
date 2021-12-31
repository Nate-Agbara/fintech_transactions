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
        TransactionResponse transactionResponse =
                transactionService.deposit(new Transaction(), transactionDto);
        if(transactionResponse.getTransactionStatus().equals(TransactionStatus.FAILED))
            return new ResponseEntity<>(transactionResponse, HttpStatus.BAD_REQUEST);

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
