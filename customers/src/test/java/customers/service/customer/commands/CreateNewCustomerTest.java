package customers.service.customer.commands;

import customers.domain.model.Customer;
import customers.service.customer.commands.CreateNewCustomer;
import customers.service.customer.factory.CustomerFactory;
import customers.service.customer.writer.CustomerWriter;
import customers.web.DTO.Requests.Customer.DTOCustomerNewProfile;
import customers.web.DTO.Responses.DTOCustomerProfileCreatedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateNewCustomerTest {

    // == fields ==
    private CustomerWriter writer;
    private CustomerFactory factory;
    private CreateNewCustomer createNewCustomer;

    @BeforeEach
    void setUp() {
        writer = mock(CustomerWriter.class);
        factory = mock(CustomerFactory.class);

        createNewCustomer = new CreateNewCustomer(factory, writer);
    }

    @Test
    void handle() {
        UUID customerId = UUID.randomUUID();
        DTOCustomerNewProfile dto = new DTOCustomerNewProfile("username", "nonhashedPassword", "26076950423",
                "email@hotmail.com", "customerName", "customerSurname");
        Customer customer = new Customer(customerId, "username", "hashedPassword", "26076950423",
                "email@hotmail.com", "customerName", "customerSurname");
        Customer savedCustomer = new Customer(customerId, "username", "hashedPassword", "26076950423",
                "email@hotmail.com", "customerName", "customerSurname");

        when(factory.create(dto)).thenReturn(customer);
        when(writer.insertNewUser(customer)).thenReturn(savedCustomer);

        DTOCustomerProfileCreatedResponse response = createNewCustomer.handle(dto);

        verify(factory).create(dto);
        verify(writer).insertNewUser(customer);

        assertEquals(savedCustomer.getCustomerId(), response.customerId());
        assertEquals(savedCustomer.getUsername(), response.username());
        assertEquals(savedCustomer.getCustomerName(), response.customerName());

        verifyNoMoreInteractions(factory, writer);
    }
}