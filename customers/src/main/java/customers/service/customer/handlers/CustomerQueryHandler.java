package customers.service.customer.handlers;

import customers.domain.model.Customer;
import customers.service.customer.queries.CustomerQueries;
import customers.web.DTO.Responses.DTOCustomerProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerQueryHandler {

    // == Fields ==
    private final CustomerQueries customerQueries;

    // == Constructors ==
    @Autowired
    public CustomerQueryHandler(CustomerQueries customerQueries) {
        this.customerQueries = customerQueries;
    }

    // == Public Methods ==
    @Transactional(readOnly = true)
    public DTOCustomerProfileResponse getById(UUID customerId) {
        return customerQueries.id(customerId);
    }

    @Transactional(readOnly = true)
    public DTOCustomerProfileResponse getByUsername(String username) {
        return customerQueries.username(username);
    }

    @Transactional(readOnly = true)
    public DTOCustomerProfileResponse getByEmail(String email) {
        return customerQueries.email(email);
    }

    @Transactional(readOnly = true)
    public DTOCustomerProfileResponse getByPhoneNumber(String phoneNumber) {
        return customerQueries.phoneNumber(phoneNumber);
    }

    // == Internal Use for Customer Microservice directly ==
    @Transactional(readOnly = true)
    public Customer getCustomer(UUID customerId){
        return customerQueries.getCustomer(customerId);
    }
}
