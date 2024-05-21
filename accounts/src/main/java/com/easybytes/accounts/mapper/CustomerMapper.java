package com.easybytes.accounts.mapper;

import com.easybytes.accounts.dto.CustomerDto;
import com.easybytes.accounts.entity.Customer;

public class CustomerMapper {
    private CustomerMapper(){}

    public static void mapToCustomer(Customer customer, CustomerDto customerDto){
        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());
        customer.setMobileNumber(customerDto.getMobileNumber());
    }

    public static void mapToCustomerDto(Customer customer, CustomerDto customerDto){
        customerDto.setEmail(customer.getEmail());
        customerDto.setName(customer.getName());
        customerDto.setMobileNumber(customer.getMobileNumber());
    }
}
