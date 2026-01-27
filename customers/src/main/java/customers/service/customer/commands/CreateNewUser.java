package customers.service.customer.commands;

import customers.service.customer.factory.CustomerFactory;
import customers.web.DTO.Requests.Customer.DTOCustomerNewProfile;
import customers.web.DTO.Responses.DTOCustomerProfileCreatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateNewUser {

    // == Fields ==
    private final CustomerFactory factory;

    // == Constructors ==
    @Autowired
    public CreateNewUser(CustomerFactory factory) {
        this.factory = factory;
    }

    // == Public Methods ==

    public DTOCustomerProfileCreatedResponse handle(DTOCustomerNewProfile dto){

    }
}
