package com.crud.security.service;

import com.crud.security.exception.DataNotFoundException;
import com.crud.security.model.Customer;
import com.crud.security.model.response.ResponseModel;
import com.crud.security.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static com.crud.security.constants.StatusResponseEnum.*;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public ResponseModel<Customer> createCustomer(Customer request) {
        Customer customer = customerRepository.save(request);
        return new ResponseModel<Customer>(RESPONSE_STATUS_CS2010).setDataObj(customer);
    }

    public ResponseModel<List<Customer>> retrieveAllCustomers() {
        List<Customer> response = customerRepository.findAll();
        if (CollectionUtils.isEmpty(response)) {
            throw new DataNotFoundException(RESPONSE_STATUS_CS4040.getCode(), HttpStatus.NOT_FOUND);
        }
        return new ResponseModel<List<Customer>>(RESPONSE_STATUS_CS2000).setDataObj(response);
    }

    public ResponseModel<Customer> retrieveCustomer(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(!customer.isPresent()) {
            throw new DataNotFoundException(RESPONSE_STATUS_CS4040.getCode(), HttpStatus.NOT_FOUND);
        }
        return new ResponseModel<Customer>(RESPONSE_STATUS_CS2000).setDataObj(customer.get());
    }

    public ResponseModel<Void> updateCustomer(Integer id, Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(!customerOptional.isPresent()) {
            throw new DataNotFoundException(RESPONSE_STATUS_CS4040.getCode(), HttpStatus.NOT_FOUND);
        }
        customer.setId(id);
        customerRepository.save(customer);
        return new ResponseModel<Void>(RESPONSE_STATUS_CS2000);
    }

    public ResponseModel<Void> deleteCustomer(Integer id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (!customerOpt.isPresent()) {
            throw new DataNotFoundException(RESPONSE_STATUS_CS4040.getCode(), HttpStatus.NOT_FOUND);
        }
        customerRepository.deleteById(id);
        return new ResponseModel<Void>(RESPONSE_STATUS_CS2000);
    }
}
