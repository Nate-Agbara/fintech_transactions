package com.fintech.transaction.repository;

import com.fintech.transaction.model.Account;
import com.fintech.transaction.model.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author: Nathan
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query(value = "select customerid, account_no, balance from account where account_no = ?",
            nativeQuery = true)
    @Transactional
    Optional<String> findByAccount(long accountNo);

    @Modifying
    @Query(value = "update account set balance = ? where account_no = ? and customerid = ? "
            , nativeQuery = true)
    @Transactional
    void  updateBalance(BigDecimal balance, long account, long customerid);

    @Query(value = "select * from transaction where transaction_ref = ?",
            nativeQuery = true)
    @Transactional
    Optional<Transaction> findTransactionByRef(long accountNo);
}
