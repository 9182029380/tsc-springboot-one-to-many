package com.example.banking.service;

import com.example.banking.entity.BankCustomer;
import com.example.banking.repository.BankCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankCustomerService {

    private final BankCustomerRepository customerRepository;

    public BankCustomerService(BankCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<BankCustomer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public BankCustomer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public BankCustomer saveCustomer(BankCustomer customer) {
        return customerRepository.save(customer);
    }

    public BankCustomer updateCustomer(Long id, BankCustomer updatedCustomer) {
        BankCustomer existing = getCustomerById(id);
        existing.setName(updatedCustomer.getName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setAccounts(updatedCustomer.getAccounts());
        return customerRepository.save(existing);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}