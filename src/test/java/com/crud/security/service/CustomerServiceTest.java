package com.crud.security.service;

import com.crud.security.exception.DataNotFoundException;
import com.crud.security.model.Customer;
import com.crud.security.model.response.ResponseModel;
import com.crud.security.repository.CustomerRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;

    @Nested
    class groupTestServiceCreateCustomer {
        @Test
        public void testCreateCustomerThenSuccess() {
            Customer customer = Customer.builder()
                    .id(1)
                    .fullName("fullName1")
                    .lastName("lastName")
                    .phone("xxxxxx")
                    .age(18)
                    .build();
            when(customerRepository.save(any())).thenReturn(customer);
            ResponseModel<Customer> responseModel = customerService.createCustomer(any());
            Customer actualResponse = responseModel.getDataObj();
            assertThat(responseModel.getStatus().getCode(), equalTo("CS2010"));
            assertThat(responseModel.getStatus().getMessage(), equalTo("Created"));
            assertThat(responseModel.getStatus().getDescription(), equalTo("Information saved"));
            assertThat(actualResponse.getFullName(), equalTo("fullName1"));
            assertThat(actualResponse.getLastName(), equalTo("lastName"));
            assertThat(actualResponse.getPhone(), equalTo("xxxxxx"));
            assertThat(actualResponse.getAge(), equalTo(18));
        }
    }

    @Nested
    class groupTestServiceRetrieveAllCustomers {
        @Test
        public void testRetrieveAllCustomersThenSuccess() {
            Customer customer1 = Customer.builder()
                    .id(1)
                    .fullName("fullName1")
                    .lastName("lastName")
                    .phone("081111111111")
                    .age(18)
                    .build();
            Customer customer2 = Customer.builder()
                    .id(2)
                    .fullName("fullName2")
                    .lastName("lastName2")
                    .phone("0822222222")
                    .age(20)
                    .build();
            List<Customer> customerList = Arrays.asList(customer1, customer2);
            when(customerRepository.findAll()).thenReturn(customerList);
            ResponseModel<List<Customer>> responseModel = customerService.retrieveAllCustomers();
            List<Customer> actualResponse = responseModel.getDataObj();
            assertThat(responseModel.getStatus().getCode(), equalTo("CS2000"));
            assertThat(responseModel.getStatus().getMessage(), equalTo("Success"));
            assertThat(responseModel.getStatus().getDescription(), equalTo("Success"));

            assertThat(actualResponse.get(0).getFullName(), equalTo(customer1.getFullName()));
            assertThat(actualResponse.get(0).getLastName(), equalTo(customer1.getLastName()));
            assertThat(actualResponse.get(0).getPhone(), equalTo(customer1.getPhone()));
            assertThat(actualResponse.get(0).getAge(), equalTo(customer1.getAge()));

            assertThat(actualResponse.get(1).getFullName(), equalTo(customer2.getFullName()));
            assertThat(actualResponse.get(1).getLastName(), equalTo(customer2.getLastName()));
            assertThat(actualResponse.get(1).getPhone(), equalTo(customer2.getPhone()));
            assertThat(actualResponse.get(1).getAge(), equalTo(customer2.getAge()));
        }

        @Test
        public void testRetrieveAllCustomersWhenNoCustomerThenNotFound() {
            when(customerRepository.findAll()).thenReturn(Collections.emptyList());
            DataNotFoundException ex = assertThrows(DataNotFoundException.class, () -> {
                customerService.retrieveAllCustomers();
            });
            assertThat(ex.getCode(), equalTo("CS4040"));
        }
    }

    @Nested
    class groupTestServiceRetrieveCustomerById {
        @Test
        public void testRetrieveCustomerByIdThenSuccess() {
            Customer customer = Customer.builder()
                    .id(1)
                    .fullName("fullName1")
                    .lastName("lastName")
                    .phone("xxxxxx")
                    .age(18)
                    .build();
            when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
            ResponseModel<Customer> responseModel = customerService.retrieveCustomer(any());
            Customer actualResponse = responseModel.getDataObj();
            assertThat(responseModel.getStatus().getCode(), equalTo("CS2000"));
            assertThat(responseModel.getStatus().getMessage(), equalTo("Success"));
            assertThat(responseModel.getStatus().getDescription(), equalTo("Success"));
            assertThat(actualResponse.getFullName(), equalTo("fullName1"));
            assertThat(actualResponse.getLastName(), equalTo("lastName"));
            assertThat(actualResponse.getPhone(), equalTo("xxxxxx"));
            assertThat(actualResponse.getAge(), equalTo(18));
        }

        @Test
        public void testRetrieveCustomerByIdWhenNoCustomerThenNotFound() {
            when(customerRepository.findAll()).thenReturn(Collections.emptyList());
            DataNotFoundException ex = assertThrows(DataNotFoundException.class, () -> {
                customerService.retrieveCustomer(1);
            });
            assertThat(ex.getCode(), equalTo("CS4040"));
        }
    }

    @Nested
    class groupTestServiceUpdateCustomerById {
        @Test
        public void testUpdateCustomerByIdThenSuccess() {
            Customer customerReq = Customer.builder()
                    .fullName("fullName2")
                    .lastName("lastName2")
                    .phone("xxxxxx")
                    .age(20)
                    .build();
            Customer customer = Customer.builder()
                    .id(1)
                    .fullName("fullName1")
                    .lastName("lastName")
                    .phone("xxxxxx")
                    .age(18)
                    .build();
            when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
            when(customerRepository.save(any())).thenReturn(customer);
            ResponseModel<Void> responseModel = customerService.updateCustomer(1, customerReq);
            assertThat(responseModel.getStatus().getCode(), equalTo("CS2000"));
            assertThat(responseModel.getStatus().getMessage(), equalTo("Success"));
            assertThat(responseModel.getStatus().getDescription(), equalTo("Success"));
        }

        @Test
        public void testUpdateCustomerByIdWhenNoCustomerThenNotFound() {
            Customer customerReq = Customer.builder()
                    .id(1)
                    .fullName("fullName1")
                    .lastName("lastName")
                    .phone("xxxxxx")
                    .age(18)
                    .build();
            when(customerRepository.findById(any())).thenReturn(Optional.empty());
            DataNotFoundException ex = assertThrows(DataNotFoundException.class, () -> {
                customerService.updateCustomer(1, customerReq);
            });
            assertThat(ex.getCode(), equalTo("CS4040"));
        }
    }

    @Nested
    class groupTestServiceDeleteCustomerById {
        @Test
        public void testDeleteCustomerByIdThenSuccess() {
            Customer customer = Customer.builder()
                    .id(1)
                    .fullName("fullName1")
                    .lastName("lastName")
                    .phone("xxxxxx")
                    .age(18)
                    .build();
            when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
            doNothing().when(customerRepository).deleteById(any());
            ResponseModel<Void> responseModel = customerService.deleteCustomer(any());
            assertThat(responseModel.getStatus().getCode(), equalTo("CS2000"));
            assertThat(responseModel.getStatus().getMessage(), equalTo("Success"));
            assertThat(responseModel.getStatus().getDescription(), equalTo("Success"));
        }

        @Test
        public void testDeleteCustomerByIdWhenNoCustomerThenNotFound() {
            when(customerRepository.findById(any())).thenReturn(Optional.empty());
            DataNotFoundException ex = assertThrows(DataNotFoundException.class, () -> {
                customerService.deleteCustomer(1);
            });
        }
    }
}
