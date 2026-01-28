package customers.service.customer.queries;

import customers.domain.model.Customer;
import customers.repositories.CustomerRepository;
import customers.web.DTO.Responses.Customer.DTOCustomerProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class CustomerQueries {

    // == Fields ==
    private final CustomerRepository repository;

    // == Constructors ==
    @Autowired
    public CustomerQueries(CustomerRepository repository) {
        this.repository = repository;
    }

    // == Public Methods ==
    public DTOCustomerProfileResponse id(UUID customerId){
        return toProfile(() -> repository.findById(customerId), "customerId", customerId);
    }

    public DTOCustomerProfileResponse phoneNumber(String phoneNumber){
        return toProfile(() -> repository.findByPhoneNumber(phoneNumber), "phone_number", phoneNumber);
    }

    public DTOCustomerProfileResponse email(String email){
        return toProfile(() -> repository.findByEmail(email), "email", email);
    }

    public DTOCustomerProfileResponse username(String userName){
        return toProfile(() -> repository.findByUsername(userName), "username", userName);
    }

    // == Internal ==
    public Customer getCustomer(UUID customerId){
        return repository.findById(customerId).orElseThrow( () -> new NoSuchElementException("No customer could be found with customerId:  " + customerId));
    }

    // == Private Methods ==

    private DTOCustomerProfileResponse toProfile(Supplier<Optional<Customer>> customerFinder, String lookupField, Object lookupValue){
        Customer customer = customerFinder.get().orElseThrow(() -> new NoSuchElementException("No customer could be found with [" + lookupField + "]: " + lookupValue));
        return new DTOCustomerProfileResponse(
                customer.getUsername(),
                customer.getCustomerName(),
                customer.getCustomerSurname(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }
}
