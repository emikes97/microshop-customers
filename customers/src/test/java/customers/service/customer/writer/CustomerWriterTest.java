package customers.service.customer.writer;

import customers.domain.model.Customer;
import customers.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class CustomerWriterTest {

    private CustomerRepository repository;
    private CustomerWriter writer;

    @BeforeEach
    void setUp() {
        repository = mock(CustomerRepository.class);
        writer = new CustomerWriter(repository);
    }

    @Test
    void insertNewUser_delegatesToRepository_andReturnsCustomer() {
        // Arrange
        Customer customer = mock(Customer.class);

        when(repository.insert(customer)).thenReturn(customer);

        // Act
        Customer result = writer.insertNewUser(customer);

        // Assert
        assertSame(customer, result);
        verify(repository).insert(customer);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void updateCustomer_delegatesToRepository() {
        // Arrange
        Customer customer = mock(Customer.class);
        int expectedVersion = 5;

        // Act
        writer.updateCustomer(customer, expectedVersion);

        // Assert
        verify(repository).updateProfile(customer, expectedVersion);
        verifyNoMoreInteractions(repository);
    }
}