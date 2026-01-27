package customers.service.customer.handlers;

import customers.service.customer.commands.CreateNewCustomer;
import customers.service.customer.commands.UpdateCustomerInformation;
import customers.web.DTO.Requests.Customer.DTOCustomerNewProfile;
import customers.web.DTO.Requests.Customer.DTOCustomerUpdateProfile;
import customers.web.DTO.Responses.DTOCustomerProfileCreatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerCommandHandler {

    // == Fields ==
    private final CreateNewCustomer createNewCustomer;
    private final UpdateCustomerInformation updateCustomerInformation;

    // == Constructors ==
    @Autowired
    public CustomerCommandHandler(CreateNewCustomer createNewCustomer, UpdateCustomerInformation updateCustomerInformation) {
        this.createNewCustomer = createNewCustomer;
        this.updateCustomerInformation = updateCustomerInformation;
    }

    @Transactional
    public DTOCustomerProfileCreatedResponse createCustomer(DTOCustomerNewProfile dto){
        return createNewCustomer.handle(dto);
    }

    @Transactional
    public void updateCustomer(UUID customerId, DTOCustomerUpdateProfile dto){
        updateCustomerInformation.handle(customerId, dto);
    }
}
