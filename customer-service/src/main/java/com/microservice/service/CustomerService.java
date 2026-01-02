package com.microservice.service;

import com.microservice.dto.CustomerRequestDTO;
import com.microservice.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    CustomerResponseDTO registerCustomer(CustomerRequestDTO dto);

    CustomerResponseDTO getCustomerById(Long id);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO dto);

    void deleteCustomer(Long id);
}
