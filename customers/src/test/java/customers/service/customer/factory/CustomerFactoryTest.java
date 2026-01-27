package customers.service.customer.factory;

import customers.domain.model.Customer;
import customers.web.DTO.Requests.Customer.DTOCustomerNewProfile;
import customers.web.DTO.Requests.Customer.DTOCustomerUpdateProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerFactoryTest {

    private PasswordEncoder encoder;
    private CustomerFactory factory;

    @BeforeEach
    void setUp() {
        encoder = mock(PasswordEncoder.class);
        factory = new CustomerFactory(encoder);
    }

    @Test
    void create_createsCustomerWithEncodedPassword() {
        DTOCustomerNewProfile dto = new DTOCustomerNewProfile(
                "username",
                "plainPassword",
                "26076950423",
                "email@hotmail.com",
                "Name",
                "Surname"
        );

        when(encoder.encode("plainPassword")).thenReturn("hashedPassword");

        // Act
        Customer customer = factory.create(dto);

        // Assert
        assertNotNull(customer.getCustomerId());
        assertEquals("username", customer.getUsername());
        assertEquals("hashedPassword", customer.getPasswordHash());
        assertEquals("26076950423", customer.getPhoneNumber());
        assertEquals("email@hotmail.com", customer.getEmail());
        assertEquals("Name", customer.getCustomerName());
        assertEquals("Surname", customer.getCustomerSurname());

        verify(encoder).encode("plainPassword");
        verifyNoMoreInteractions(encoder);
    }

    @Test
    void updateCustomer_updatesOnlyProvidedFields() {
        Customer existing = new Customer(
                UUID.randomUUID(),
                "oldUsername",
                "hashedPassword",
                "111",
                "old@email.com",
                "OldName",
                "OldSurname"
        );

        DTOCustomerUpdateProfile dto = new DTOCustomerUpdateProfile(
                " newUsername ",
                null,
                " new@email.com ",
                null,
                null
        );

        // Act
        Customer updated = factory.updateCustomer(existing, dto);

        // Assert
        assertEquals(existing.getCustomerId(), updated.getCustomerId());
        assertEquals("newUsername", updated.getUsername());
        assertEquals("hashedPassword", updated.getPasswordHash());
        assertEquals("111", updated.getPhoneNumber());              // unchanged
        assertEquals("new@email.com", updated.getEmail());
        assertEquals("OldName", updated.getCustomerName());         // unchanged
        assertEquals("OldSurname", updated.getCustomerSurname());   // unchanged
    }

    @Test
    void updateCustomer_ignoresBlankValues() {
        // Arrange
        Customer existing = new Customer(
                UUID.randomUUID(),
                "username",
                "hashedPassword",
                "111",
                "email@email.com",
                "Name",
                "Surname"
        );

        DTOCustomerUpdateProfile dto = new DTOCustomerUpdateProfile(
                "   ",   // blank
                "",      // blank
                null,
                "   ",   // blank
                null
        );

        // Act
        Customer updated = factory.updateCustomer(existing, dto);

        // Assert
        assertEquals(existing.getUsername(), updated.getUsername());
        assertEquals(existing.getPhoneNumber(), updated.getPhoneNumber());
        assertEquals(existing.getEmail(), updated.getEmail());
        assertEquals(existing.getCustomerName(), updated.getCustomerName());
        assertEquals(existing.getCustomerSurname(), updated.getCustomerSurname());
    }
}