package com.easybytes.accounts.service.impl;

import com.easybytes.accounts.constants.AccountConstants;
import com.easybytes.accounts.dto.CustomerDto;
import com.easybytes.accounts.entity.Accounts;
import com.easybytes.accounts.entity.Customer;
import com.easybytes.accounts.exception.CustomerAlreadyExistsException;
import com.easybytes.accounts.mapper.CustomerMapper;
import com.easybytes.accounts.repository.AccountsRepository;
import com.easybytes.accounts.repository.CustomerRepository;
import com.easybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = new Customer();
        CustomerMapper.mapToCustomer(customer,customerDto);

        Optional<Customer> optionalCustomer = customerRepository
                .findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent())
            throw new CustomerAlreadyExistsException("Customer already registered with the mobile number: "
            + optionalCustomer.get().getMobileNumber());

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy(customerDto.getName());
        Customer savedCustomer = customerRepository.save(customer);

        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer){
        Accounts account = new Accounts();
        account.setCustomerId(customer.getCustomerId());
        long randomAccNum = 100000000L + new Random().nextInt(90000000);

        account.setAccountNumber(randomAccNum);
        account.setAccountType(AccountConstants.SAVINGS);
        account.setBranchAddress(AccountConstants.ADDRESS);
        account.setCreatedBy(customer.getCreatedBy());
        account.setCreatedAt(customer.getCreatedAt());
        return account;
    }
}
