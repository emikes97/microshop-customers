package customers.service.customer.commands;

import customers.domain.model.Customer;
import customers.domain.state.AccountStatus;
import customers.service.customer.handlers.CustomerQueryHandler;
import customers.service.customer.writer.CustomerWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateCustomerStatus {

    // == Fields ==
    private final CustomerQueryHandler customerQueryHandler;
    private final CustomerWriter customerWriter;

    // == Constructors ==
    @Autowired
    public UpdateCustomerStatus(CustomerQueryHandler customerQueryHandler, CustomerWriter customerWriter) {
        this.customerQueryHandler = customerQueryHandler;
        this.customerWriter = customerWriter;
    }

    public void handle(UUID customerId, AccountStatus status){
        Customer customer = customerQueryHandler.getCustomer(customerId);

        if(customer.getStatus() == status)
            throw new IllegalStateException("Error: Customer state is already: " + status.toString());

        if(!customerWriter.updateAccountState(customerId, status, customer.getVersion()))
            throw new IllegalStateException("Stale version: customer was updated by another request");
    }
}
