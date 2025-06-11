package com.example.banking.service;

import com.example.banking.entity.BankAccount;
import com.example.banking.entity.BankCustomer;
import com.example.banking.repository.BankAccountRepository;
import com.example.banking.repository.BankCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountService {

    private final BankAccountRepository accountRepository;
    private final BankCustomerRepository customerRepository;

    public BankAccountService(BankAccountRepository accountRepository, BankCustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public BankAccount addAccountToCustomer(Long customerId, BankAccount account) {
        BankCustomer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        account.setCustomer(customer);
        return accountRepository.save(account);
    }

    public BankAccount updateAccount(Long id, BankAccount updatedAccount) {
        return accountRepository.findById(id).map(account -> {
            account.setAccountNumber(updatedAccount.getAccountNumber());
            account.setBalance(updatedAccount.getBalance());
            return accountRepository.save(account);
        }).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public List<BankAccount> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findAll().stream()
                .filter(account -> account.getCustomer() != null && account.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());
    }
}