package com.microservice.service;

import com.microservice.dto.CustomerRequestDTO;
import com.microservice.dto.CustomerResponseDTO;
import com.microservice.entity.Customer;
import com.microservice.exception.CustomerAlreadyExistsException;
import com.microservice.exception.CustomerNotFoundException;
import com.microservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public CustomerResponseDTO registerCustomer(CustomerRequestDTO dto) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new CustomerAlreadyExistsException("Email already registered: " + dto.getEmail());
        }

        if (repository.existsByMobile(dto.getMobile())) {
            throw new CustomerAlreadyExistsException("Mobile already registered: " + dto.getMobile());
        }

        Customer c = new Customer();
        c.setFirstName(dto.getFirstName());
        c.setLastName(dto.getLastName());
        c.setEmail(dto.getEmail());
        c.setMobile(dto.getMobile());
        c.setAddress(dto.getAddress());

        Customer saved = repository.save(c);

        return mapToResponse(saved);
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {

        Customer c = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        return mapToResponse(c);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO dto) {

        Customer c = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        c.setFirstName(dto.getFirstName());
        c.setLastName(dto.getLastName());
        c.setEmail(dto.getEmail());
        c.setMobile(dto.getMobile());
        c.setAddress(dto.getAddress());

        Customer updated = repository.save(c);

        return mapToResponse(updated);
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer c = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        repository.delete(c);
    }

    private CustomerResponseDTO mapToResponse(Customer c) {

        CustomerResponseDTO res = new CustomerResponseDTO();

        res.setId(c.getId());
        res.setFirstName(c.getFirstName());
        res.setLastName(c.getLastName());
        res.setEmail(c.getEmail());
        res.setMobile(c.getMobile());
        res.setAddress(c.getAddress());

        return res;
    }
}
