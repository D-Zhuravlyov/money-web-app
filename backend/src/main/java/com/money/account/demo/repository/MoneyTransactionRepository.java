package com.money.account.demo.repository;

import com.money.account.demo.model.MoneyTransaction;
import com.money.account.demo.model.User;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction, Long> {

    @ReadOnlyProperty
    @Query("select t from MoneyTransaction t where t.user = :user")
    List<MoneyTransaction> findByUserReturnStream(@Param("user") User user);

}
