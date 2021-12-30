package com.fintech.transaction.dto;

import com.fintech.transaction.model.TransactionStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Nathan
 */
@Data
@ApiModel(description = "Transaction Request model")
public class TransactionDto {

    @ApiModelProperty(notes = "id", name = "id", value = "0")
    private int id;

    @ApiModelProperty(notes = "account Number", name = "accountNo", value = "000000000")
    private long accountNo;

    @ApiModelProperty(notes = "customerID", name = "customerId", value = "0000000")
    private long customerId;

    @ApiModelProperty(notes = "transactionRef", name = "transactionRef", value = "45677889876")
    private String transactionRef;

    @ApiModelProperty(notes = "Amount", name = "amount", value = "10.2")
    private BigDecimal amount;

    @ApiModelProperty(notes = "transactionRemark", name = "transactionRemark", value = "Transfer")
    private String transactionRemark;

    @ApiModelProperty(notes = "transactionType", name = "transactionType", value = "Deposit")
    private String transactionType;
}
