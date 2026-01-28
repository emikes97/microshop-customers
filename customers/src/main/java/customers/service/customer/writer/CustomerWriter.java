package customers.service.customer.writer;

import customers.domain.model.Customer;
import customers.domain.state.AccountStatus;
import customers.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerWriter {

    // == Fields ==
    private final CustomerRepository customerRepository;

    // == Constructors ==
    @Autowired
    public CustomerWriter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // == Public Methods ==

    public Customer insertNewUser(Customer customer){
        return customerRepository.insert(customer);
    }

    public void updateCustomer(Customer customer, int expectedVersion){
        customerRepository.updateProfile(customer, expectedVersion);
    }

    public boolean updateCredentials(UUID customerId, String hashedPassword, int expectedVersion){
        return customerRepository.updatePassword(customerId, hashedPassword, expectedVersion);
    }

    public boolean updateAccountState(UUID customerId, AccountStatus status, int expectedVersion){
        return customerRepository.updateStatus(customerId, status, expectedVersion);
    }
}
