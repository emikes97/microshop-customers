package customers.repositories;

import customers.domain.model.Customer;
import customers.domain.state.AccountStatus;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    // == Find Customer ==
    Optional<Customer> findById(UUID id);
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNumber(String phoneNumber);

    // == DB Customer actions ==
    Customer insert(Customer customer);
    boolean updateProfile(Customer customer, int expectedVersion); // Used for multiple updates in a customer
    boolean updateStatus(UUID customerId, AccountStatus status, int expectedVersion);
    boolean updatePassword(UUID customerId, String newHashedPassword, int expectedVersion);
}
