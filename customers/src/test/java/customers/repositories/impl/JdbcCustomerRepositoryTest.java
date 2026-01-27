package customers.repositories.impl;

import customers.domain.model.Customer;
import customers.domain.state.AccountStatus;
import customers.repositories.mappers.CustomerRowMapper;
import customers.repositories.sql.CustomerSql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JdbcCustomerRepositoryTest {

    private JdbcTemplate jdbc;
    private CustomerRowMapper mapper;
    private JdbcCustomerRepository repository;

    @BeforeEach
    void setUp() {
        jdbc = mock(JdbcTemplate.class);
        mapper = mock(CustomerRowMapper.class);

        repository = new JdbcCustomerRepository(jdbc, mapper);
    }

    @Test
    void findById_returnsCustomer_whenFound() {
        UUID id = UUID.randomUUID();

        Customer customer = mock(Customer.class);
        when(jdbc.queryForObject(CustomerSql.FIND_BY_ID, mapper, id)).thenReturn(customer);

        Optional<Customer> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertSame(customer, result.get());

        verify(jdbc).queryForObject(CustomerSql.FIND_BY_ID, mapper, id);
        verifyNoMoreInteractions(jdbc);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void findById_returnsEmpty_whenNotFound() {
        UUID id = UUID.randomUUID();

        when(jdbc.queryForObject(CustomerSql.FIND_BY_ID, mapper, id))
                .thenThrow(new EmptyResultDataAccessException(1));

        Optional<Customer> result = repository.findById(id);

        assertTrue(result.isEmpty());

        verify(jdbc).queryForObject(CustomerSql.FIND_BY_ID, mapper, id);
        verifyNoMoreInteractions(jdbc);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void insert_returnsInsertedCustomer() {
        // Arrange
        Customer input = mock(Customer.class);
        Customer inserted = mock(Customer.class);

        UUID customerId = UUID.randomUUID();
        String username = "u";
        String passwordHash = "hash";
        String phoneNumber = "phone";
        String email = "e@e.com";
        String customerName = "name";
        String customerSurname = "surname";
        AccountStatus status = AccountStatus.ACTIVE;

        when(input.getCustomerId()).thenReturn(customerId);
        when(input.getUsername()).thenReturn(username);
        when(input.getPasswordHash()).thenReturn(passwordHash);
        when(input.getPhoneNumber()).thenReturn(phoneNumber);
        when(input.getEmail()).thenReturn(email);
        when(input.getCustomerName()).thenReturn(customerName);
        when(input.getCustomerSurname()).thenReturn(customerSurname);
        when(input.getStatus()).thenReturn(status);

        // Stub: now only 8 args after mapper
        when(jdbc.queryForObject(
                eq(CustomerSql.INSERT_CUSTOMER),
                same(mapper),
                any(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(inserted);

        // Act
        Customer result = repository.insert(input);

        // Assert
        assertSame(inserted, result);

        // Verify: exact 8 args
        verify(jdbc).queryForObject(
                eq(CustomerSql.INSERT_CUSTOMER),
                same(mapper),
                eq(customerId),
                eq(username),
                eq(passwordHash),
                eq(phoneNumber),
                eq(email),
                eq(customerName),
                eq(customerSurname),
                eq(status.name())
        );

        verifyNoMoreInteractions(jdbc);
    }

    @Test
    void insert_throws_whenInsertReturnedNull() {
        // Arrange
        Customer input = mock(Customer.class);

        UUID customerId = UUID.randomUUID();
        when(input.getCustomerId()).thenReturn(customerId);
        when(input.getUsername()).thenReturn("u");
        when(input.getPasswordHash()).thenReturn("hash");
        when(input.getPhoneNumber()).thenReturn("phone");
        when(input.getEmail()).thenReturn("e@e.com");
        when(input.getCustomerName()).thenReturn("name");
        when(input.getCustomerSurname()).thenReturn("surname");
        when(input.getStatus()).thenReturn(AccountStatus.ACTIVE);

        when(jdbc.queryForObject(
                eq(CustomerSql.INSERT_CUSTOMER),
                same(mapper),
                any(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(null);

        // Act + Assert
        IllegalStateException ex =
                assertThrows(IllegalStateException.class, () -> repository.insert(input));
        assertTrue(ex.getMessage().contains("Insert returned null"));

        verify(jdbc).queryForObject(
                eq(CustomerSql.INSERT_CUSTOMER),
                same(mapper),
                any(), any(), any(), any(), any(), any(), any(), any()
        );
        verifyNoMoreInteractions(jdbc);
    }

    @Test
    void updateProfile_returnsTrue_whenOneRowUpdated() {
        Customer customer = mock(Customer.class);
        UUID id = UUID.randomUUID();

        when(customer.getCustomerId()).thenReturn(id);
        when(customer.getUsername()).thenReturn("u");
        when(customer.getPhoneNumber()).thenReturn("p");
        when(customer.getEmail()).thenReturn("e");
        when(customer.getCustomerName()).thenReturn("n");
        when(customer.getCustomerSurname()).thenReturn("s");

        when(jdbc.update(
                CustomerSql.UPDATE_CUSTOMER,
                "u", "p", "e", "n", "s",
                id,
                5
        )).thenReturn(1);

        boolean updated = repository.updateProfile(customer, 5);

        assertTrue(updated);

        verify(jdbc).update(
                CustomerSql.UPDATE_CUSTOMER,
                "u", "p", "e", "n", "s",
                id,
                5
        );
        verifyNoMoreInteractions(jdbc);
    }

    @Test
    void updateProfile_returnsFalse_whenNoRowUpdated() {
        Customer customer = mock(Customer.class);
        UUID id = UUID.randomUUID();

        when(customer.getCustomerId()).thenReturn(id);
        when(customer.getUsername()).thenReturn("u");
        when(customer.getPhoneNumber()).thenReturn("p");
        when(customer.getEmail()).thenReturn("e");
        when(customer.getCustomerName()).thenReturn("n");
        when(customer.getCustomerSurname()).thenReturn("s");

        when(jdbc.update(anyString(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(0);

        boolean updated = repository.updateProfile(customer, 5);

        assertFalse(updated);

        verify(jdbc).update(
                CustomerSql.UPDATE_CUSTOMER,
                "u", "p", "e", "n", "s",
                id,
                5
        );
        verifyNoMoreInteractions(jdbc);
    }
}