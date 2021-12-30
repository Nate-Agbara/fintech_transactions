package com.fintech.transaction.dto;

import com.fintech.transaction.model.TransactionStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Nathan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Transaction Response model")
public class TransactionResponse {

    @ApiModelProperty(notes = "transactionRef", name = "transactionRef", value = "45677889876")
    private long transactionRef;

    @ApiModelProperty(notes = "amount", name = "amount", value = "2.00")
    private BigDecimal amount;

    @ApiModelProperty(notes = "message", name = "message", value = "SUCCESS")
    private String message;

    @ApiModelProperty(notes = "transactionRemark", name = "transactionRemark", value = "Transfer")
    private String transactionRemark;

    @ApiModelProperty(notes = "transactionType", name = "transactionType", value = "Deposit")
    private String transactionType;

    @ApiModelProperty(notes = "transactionStatus", name = "transactionStatus", value = "SUCCESS")
    private TransactionStatus transactionStatus;
}
