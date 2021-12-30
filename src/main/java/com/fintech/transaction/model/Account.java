package com.fintech.transaction.model;

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
public class Account {

    private int id;
    private long customerID;
    private String firstName;
    private String otherNames;
    private String lastName;
    private String address;
    private String gender;
    private String phone;
    private String email;
    private long accountNo;
    private BigDecimal balance;
    private String accountStatus;
    private Date createdDate;
}
