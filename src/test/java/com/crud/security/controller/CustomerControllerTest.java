package com.crud.security.controller;

import com.crud.security.exception.DataNotFoundException;
import com.crud.security.model.Customer;
import com.crud.security.model.response.ResponseModel;
import com.crud.security.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

import static com.crud.security.constants.StatusResponseEnum.RESPONSE_STATUS_CS2010;
import static com.crud.security.constants.StatusResponseEnum.RESPONSE_STATUS_CS2000;
import static com.crud.security.constants.StatusResponseEnum.RESPONSE_STATUS_CS4040;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Nested
    class groupTestControllerCreateCustomer {
        @Test
        public void testCreateCustomerWhenSuccessCreatedCustomerThenReturnSuccess() throws Exception {
            String url = UriComponentsBuilder.fromPath("/api/customers").build().toUriString();
            Customer customer = Customer.builder()
                    .id(1)
                    .fullName("fullName1")
                    .lastName("lastName")
                    .phone("xxxxxx")
                    .age(18)
                    .build();
            when(customerService.createCustomer(any()))
                    .thenReturn(new ResponseModel<Customer>(RESPONSE_STATUS_CS2010).setDataObj(customer));
            String reqBody = new ObjectMapper().writeValueAsString(customer);

            mockMvc.perform(MockMvcRequestBuilders.post(url)
                            .content(reqBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(jsonPath("$.status.code").value("CS2010"))
                    .andExpect(jsonPath("$.status.message").value("Created"))
                    .andExpect(jsonPath("$.status.description").value("Information saved"))
                    .andExpect(jsonPath("$.data.id").value(1))
                    .andExpect(jsonPath("$.data.full_name").value("fullName1"))
                    .andExpect(jsonPath("$.data.last_name").value("lastName"))
                    .andExpect(jsonPath("$.data.phone").value("xxxxxx"))
                    .andExpect(jsonPath("$.data.age").value(18));
        }
    }

    @Nested
    class groupTestControllerGetAllCustomer {
        @Test
        public void testGetAllCustomerWhenReturnListOfCustomerThenReturnSuccess() throws Exception {
            String url = UriComponentsBuilder.fromPath("/api/customers").build().toUriString();
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
            when(customerService.retrieveAllCustomers())
                    .thenReturn(new ResponseModel<List<Customer>>(RESPONSE_STATUS_CS2000).setDataObj(customerList));

            mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.status.code").value("CS2000"))
                    .andExpect(jsonPath("$.status.message").value("Success"))
                    .andExpect(jsonPath("$.status.description").value("Success"))
                    .andExpect(jsonPath("$.data[0].id").value(1))
                    .andExpect(jsonPath("$.data[0].full_name").value("fullName1"))
                    .andExpect(jsonPath("$.data[0].last_name").value("lastName"))
                    .andExpect(jsonPath("$.data[0].phone").value("081111111111"))
                    .andExpect(jsonPath("$.data[0].age").value(18))
                    .andExpect(jsonPath("$.data[1].id").value(2))
                    .andExpect(jsonPath("$.data[1].full_name").value("fullName2"))
                    .andExpect(jsonPath("$.data[1].last_name").value("lastName2"))
                    .andExpect(jsonPath("$.data[1].phone").value("0822222222"))
                    .andExpect(jsonPath("$.data[1].age").value(20));
        }

        @Test
        public void testGetAllCustomerWhenEmptyCustomerThenReturnDataNotFound() throws Exception {
            String url = UriComponentsBuilder.fromPath("/api/customers").build().toUriString();
            when(customerService.retrieveAllCustomers()).thenThrow(new DataNotFoundException(RESPONSE_STATUS_CS4040.getCode(), HttpStatus.NOT_FOUND));

            mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(jsonPath("$.status.code").value("CS4040"))
                    .andExpect(jsonPath("$.status.message").value("Data not found"))
                    .andExpect(jsonPath("$.status.description").value("Data not found"));
        }
    }

    @Nested
    class groupTestControllerGetCustomerById {
        @Test
        public void testGetCustomerByIdWhenReturnCustomerThenReturnSuccess() throws Exception {
            String url = UriComponentsBuilder.fromPath("/api/customers/1").build().toUriString();
            Customer customer = Customer.builder()
                    .id(1)
                    .fullName("fullName")
                    .lastName("lastName")
                    .phone("081111111111")
                    .age(18)
                    .build();
            when(customerService.retrieveCustomer(eq(1)))
                    .thenReturn(new ResponseModel<Customer>(RESPONSE_STATUS_CS2000).setDataObj(customer));

            mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status.code").value("CS2000"))
                    .andExpect(jsonPath("$.status.message").value("Success"))
                    .andExpect(jsonPath("$.status.description").value("Success"))
                    .andExpect(jsonPath("$.data.id").value(1))
                    .andExpect(jsonPath("$.data.full_name").value("fullName"))
                    .andExpect(jsonPath("$.data.last_name").value("lastName"))
                    .andExpect(jsonPath("$.data.phone").value("081111111111"));
        }

        @Test
        public void testGetCustomerByIdWhenServiceReturnDataNotFoundThenReturnDataNotFound() throws Exception {
            String url = UriComponentsBuilder.fromPath("/api/customers/1").build().toUriString();
            when(customerService.retrieveCustomer(eq(1)))
                    .thenThrow(new DataNotFoundException(RESPONSE_STATUS_CS4040.getCode(), HttpStatus.NOT_FOUND));

            mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status.code").value("CS4040"))
                    .andExpect(jsonPath("$.status.message").value("Data not found"))
                    .andExpect(jsonPath("$.status.description").value("Data not found"));
        }
    }

    @Nested
    class groupTestControllerUpdateCustomerById {
        @Test
        public void testUpdateCustomerWhenSuccessUpdatedCustomerThenReturnSuccess() throws Exception {
            String url = UriComponentsBuilder.fromPath("/api/customers/1").build().toUriString();
            Customer customer = Customer.builder()
                    .id(1)
                    .fullName("fullName1")
                    .lastName("lastName")
                    .phone("xxxxxx")
                    .age(18)
                    .build();
            when(customerService.updateCustomer(eq(1), any()))
                    .thenReturn(new ResponseModel<Void>(RESPONSE_STATUS_CS2000));
            String reqBody = new ObjectMapper().writeValueAsString(customer);

            mockMvc.perform(MockMvcRequestBuilders.put(url)
                            .content(reqBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.status.code").value("CS2000"))
                    .andExpect(jsonPath("$.status.message").value("Success"))
                    .andExpect(jsonPath("$.status.description").value("Success"));
        }

        @Test
        public void testUpdateCustomerWhenSendInvalidCustomerIDThenReturnNotFound() throws Exception {
            String url = UriComponentsBuilder.fromPath("/api/customers/1").build().toUriString();
            Customer customer = Customer.builder()
                    .id(1)
                    .fullName("fullName1")
                    .lastName("lastName")
                    .phone("xxxxxx")
                    .age(18)
                    .build();
            String reqBody = new ObjectMapper().writeValueAsString(customer);

            when(customerService.updateCustomer(eq(1), any()))
                    .thenThrow(new DataNotFoundException(RESPONSE_STATUS_CS4040.getCode(), HttpStatus.NOT_FOUND));
            mockMvc.perform(MockMvcRequestBuilders.put(url)
                            .content(reqBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(jsonPath("$.status.code").value("CS4040"))
                    .andExpect(jsonPath("$.status.message").value("Data not found"))
                    .andExpect(jsonPath("$.status.description").value("Data not found"));
        }
    }

    @Nested
    class groupTestControllerDeleteCustomer {
        @Test
        public void testDeleteCustomerWhenSuccessDeletedCustomerThenReturnSuccess() throws Exception {
            String url = UriComponentsBuilder.fromPath("/api/customers/1").build().toUriString();
            when(customerService.deleteCustomer(eq(1)))
                    .thenReturn(new ResponseModel<Void>(RESPONSE_STATUS_CS2000));

            mockMvc.perform(MockMvcRequestBuilders.delete(url)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.status.code").value("CS2000"))
                    .andExpect(jsonPath("$.status.message").value("Success"))
                    .andExpect(jsonPath("$.status.description").value("Success"));
        }

        @Test
        public void testDeleteCustomerWhenFailedDeletedCustomerThenReturnNotFound() throws Exception {
            String url = UriComponentsBuilder.fromPath("/api/customers/1").build().toUriString();
            when(customerService.deleteCustomer(eq(1)))
                    .thenThrow(new DataNotFoundException(RESPONSE_STATUS_CS4040.getCode(), HttpStatus.NOT_FOUND));

            mockMvc.perform(MockMvcRequestBuilders.delete(url)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(jsonPath("$.status.code").value("CS4040"))
                    .andExpect(jsonPath("$.status.message").value("Data not found"))
                    .andExpect(jsonPath("$.status.description").value("Data not found"));
        }
    }
}
