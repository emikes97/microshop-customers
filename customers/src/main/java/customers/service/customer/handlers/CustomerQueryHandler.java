package customers.service.customer.handlers;

import customers.domain.model.Customer;
import customers.service.customer.queries.CustomerQueries;
import customers.web.DTO.Responses.Customer.DTOCustomerProfileResponse;
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

    // Input goes via priority, if multiple parameters are given only one will be utilized.
    @Transactional(readOnly = true)
    public DTOCustomerProfileResponse searchCustomer(UUID customerId, String email, String phoneNumber, String username){

        if(customerId != null)
            return customerQueries.id(customerId);

        if(username != null)
            return customerQueries.username(username);

        if(email != null)
            return customerQueries.email(email);

        if(phoneNumber != null)
            return customerQueries.phoneNumber(phoneNumber);

        throw new IllegalArgumentException("No parameter was provided");
    }

    @Transactional(readOnly = true)
    public DTOCustomerProfileResponse findCustomer(UUID customerId){
        return customerQueries.id(customerId);
    }

    // == Internal Use for Customer Microservice directly ==
    @Transactional(readOnly = true)
    public Customer getCustomer(UUID customerId){
        return customerQueries.getCustomer(customerId);
    }
}
