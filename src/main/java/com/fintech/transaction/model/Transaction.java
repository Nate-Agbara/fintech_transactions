package com.fintech.transaction.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Nathan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ApiModel(description = "Transaction model")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long accountNo;
    private long customerId;
    private long transactionRef;
    private BigDecimal amount;
    private BigDecimal balance;
    private String transactionRemark;
    private String transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private boolean sentSmsNotification;
    private boolean sentEmailNotification;

    @Column(name="transactionDate", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
    private Date transactionDate;
}
