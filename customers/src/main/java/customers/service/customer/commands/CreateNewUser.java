package customers.service.customer.commands;

import customers.domain.model.Customer;
import customers.service.customer.factory.CustomerFactory;
import customers.service.customer.writer.CustomerWriter;
import customers.web.DTO.Requests.Customer.DTOCustomerNewProfile;
import customers.web.DTO.Responses.DTOCustomerProfileCreatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateNewUser {

    // == Fields ==
    private final CustomerFactory factory;
    private final CustomerWriter writer;

    // == Constructors ==
    @Autowired
    public CreateNewUser(CustomerFactory factory, CustomerWriter writer) {
        this.factory = factory;
        this.writer = writer;
    }

    // == Public Methods ==

    public DTOCustomerProfileCreatedResponse handle(DTOCustomerNewProfile dto){

        Customer customerToSave = factory.create(dto);
        Customer savedCustomer = writer.insertNewUser(customerToSave);
    }
}
