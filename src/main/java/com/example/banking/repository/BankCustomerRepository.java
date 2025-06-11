package com.example.banking.repository;

import com.example.banking.entity.BankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCustomerRepository extends JpaRepository<BankCustomer, Long> {
}