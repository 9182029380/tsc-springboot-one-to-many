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
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());

            // Clear existing accounts and re-add from incoming request (if needed)
            existingCustomer.getAccounts().clear();

            if (updatedCustomer.getAccounts() != null) {
                updatedCustomer.getAccounts().forEach(account -> {
                    account.setCustomer(existingCustomer); // Set the back-reference
                    existingCustomer.getAccounts().add(account);
                });
            }

            return customerRepository.save(existingCustomer);
        }).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }


    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}