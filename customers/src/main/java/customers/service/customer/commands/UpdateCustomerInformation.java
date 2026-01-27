package customers.service.customer.commands;

import customers.domain.model.Customer;
import customers.service.customer.factory.CustomerFactory;
import customers.service.customer.handlers.CustomerQueryHandler;
import customers.service.customer.writer.CustomerWriter;
import customers.web.DTO.Requests.Customer.DTOCustomerUpdateProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateCustomerInformation {

    // == Fields ==
    private final CustomerWriter writer;
    private final CustomerQueryHandler customerQueryHandler;
    private final CustomerFactory factory;

    // == Constructors ==
    @Autowired
    public UpdateCustomerInformation(CustomerWriter writer, CustomerQueryHandler customerQueryHandler, CustomerFactory factory) {
        this.writer = writer;
        this.customerQueryHandler = customerQueryHandler;
        this.factory = factory;
    }

    // == Public Methods ==
    public void handle(UUID customerId, DTOCustomerUpdateProfile dto){
        if(noOperation(dto)) return;
        Customer customer = customerQueryHandler.getCustomer(customerId);
        Customer updatedCustomer =  factory.updateCustomer(customer, dto);
        writer.updateCustomer(updatedCustomer, customer.getVersion());
    }

    // == Private Methods ==
    private boolean noOperation(DTOCustomerUpdateProfile dto){
        return (dto.username() == null || dto.username().trim().isBlank()) &&
                (dto.customerSurname() == null || dto.customerSurname().trim().isBlank()) &&
                (dto.customerName() == null || dto.customerName().trim().isBlank()) &&
                (dto.email() == null || dto.email().trim().isBlank()) &&
                (dto.phoneNumber() == null || dto.phoneNumber().trim().isBlank());
    }
}
