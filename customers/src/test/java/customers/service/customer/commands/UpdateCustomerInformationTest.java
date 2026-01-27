package customers.service.customer.commands;

import customers.domain.model.Customer;
import customers.service.customer.factory.CustomerFactory;
import customers.service.customer.handlers.CustomerQueryHandler;
import customers.service.customer.writer.CustomerWriter;
import customers.web.DTO.Requests.Customer.DTOCustomerUpdateProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class UpdateCustomerInformationTest {

    private CustomerWriter writer;
    private CustomerQueryHandler customerQueryHandler;
    private CustomerFactory factory;

    private UpdateCustomerInformation updateCustomerInformation;

    @BeforeEach
    void setUp() {
        writer = mock(CustomerWriter.class);
        customerQueryHandler = mock(CustomerQueryHandler.class);
        factory = mock(CustomerFactory.class);

        updateCustomerInformation = new UpdateCustomerInformation(writer, customerQueryHandler, factory);
    }

    @Test
    void handle_returnsEarly_whenDtoIsNoOp() {
        // Arrange: all null => noOperation == true
        UUID customerId = UUID.randomUUID();
        DTOCustomerUpdateProfile dto = new DTOCustomerUpdateProfile(null, null, null, null, null);

        // Act
        updateCustomerInformation.handle(customerId, dto);

        // Assert: nothing called
        verifyNoInteractions(customerQueryHandler, factory, writer);
    }

    @Test
    void handle_returnsEarly_whenDtoIsWhitespaceOnly() {
        // Arrange: whitespace -> trim -> blank => noOperation == true
        UUID customerId = UUID.randomUUID();
        DTOCustomerUpdateProfile dto = new DTOCustomerUpdateProfile("   ", "   ", "   ", "   ", "   ");

        // Act
        updateCustomerInformation.handle(customerId, dto);

        // Assert
        verifyNoInteractions(customerQueryHandler, factory, writer);
    }

    @Test
    void handle_updatesCustomer_whenDtoHasAtLeastOneValue() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        DTOCustomerUpdateProfile dto = new DTOCustomerUpdateProfile("newUser", null, null, null, null);

        Customer existingCustomer = mock(Customer.class);
        Customer updatedCustomer = mock(Customer.class);

        when(customerQueryHandler.getCustomer(customerId)).thenReturn(existingCustomer);
        when(existingCustomer.getVersion()).thenReturn(7);

        when(factory.updateCustomer(existingCustomer, dto)).thenReturn(updatedCustomer);

        // Act
        updateCustomerInformation.handle(customerId, dto);

        // Assert
        verify(customerQueryHandler).getCustomer(customerId);
        verify(factory).updateCustomer(existingCustomer, dto);
        verify(writer).updateCustomer(updatedCustomer, 7);

        verifyNoMoreInteractions(customerQueryHandler, factory, writer);
    }
}