package customers.service.customer.queries;

import customers.domain.model.Customer;
import customers.repositories.CustomerRepository;
import customers.web.DTO.Responses.Customer.DTOCustomerProfileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerQueriesTest {

    private CustomerRepository repository;
    private CustomerQueries queries;

    @BeforeEach
    void setUp() {
        repository = mock(CustomerRepository.class);
        queries = new CustomerQueries(repository);
    }

    @Test
    void id_returnsProfile_whenCustomerExists() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Customer customer = mock(Customer.class);

        when(repository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customer.getUsername()).thenReturn("username");
        when(customer.getCustomerName()).thenReturn("Name");
        when(customer.getCustomerSurname()).thenReturn("Surname");
        when(customer.getEmail()).thenReturn("email@test.com");
        when(customer.getPhoneNumber()).thenReturn("123");

        // Act
        DTOCustomerProfileResponse response = queries.id(customerId);

        // Assert
        assertEquals("username", response.username());
        assertEquals("Name", response.customerName());
        assertEquals("Surname", response.customerSurname());
        assertEquals("email@test.com", response.email());
        assertEquals("123", response.phoneNumber());

        verify(repository).findById(customerId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void id_throws_whenCustomerDoesNotExist() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        when(repository.findById(customerId)).thenReturn(Optional.empty());

        // Act + Assert
        NoSuchElementException ex =
                assertThrows(NoSuchElementException.class, () -> queries.id(customerId));

        assertTrue(ex.getMessage().contains("customerId"));

        verify(repository).findById(customerId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void email_returnsProfile_whenCustomerExists() {
        // Arrange
        String email = "email@test.com";
        Customer customer = mock(Customer.class);

        when(repository.findByEmail(email)).thenReturn(Optional.of(customer));
        when(customer.getUsername()).thenReturn("username");
        when(customer.getCustomerName()).thenReturn("Name");
        when(customer.getCustomerSurname()).thenReturn("Surname");
        when(customer.getEmail()).thenReturn(email);
        when(customer.getPhoneNumber()).thenReturn("123");

        // Act
        DTOCustomerProfileResponse response = queries.email(email);

        // Assert
        assertEquals("username", response.username());
        assertEquals("Name", response.customerName());
        assertEquals("Surname", response.customerSurname());
        assertEquals(email, response.email());
        assertEquals("123", response.phoneNumber());

        verify(repository).findByEmail(email);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getCustomer_returnsCustomer_whenExists() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Customer customer = mock(Customer.class);

        when(repository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        Customer result = queries.getCustomer(customerId);

        // Assert
        assertSame(customer, result);

        verify(repository).findById(customerId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getCustomer_throws_whenNotFound() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        when(repository.findById(customerId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NoSuchElementException.class,
                () -> queries.getCustomer(customerId));

        verify(repository).findById(customerId);
        verifyNoMoreInteractions(repository);
    }
}