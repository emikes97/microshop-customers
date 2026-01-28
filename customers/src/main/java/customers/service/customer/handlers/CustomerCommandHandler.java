package customers.service.customer.handlers;

import customers.domain.state.AccountStatus;
import customers.service.customer.commands.CreateNewCustomer;
import customers.service.customer.commands.UpdateCustomerCredentials;
import customers.service.customer.commands.UpdateCustomerInformation;
import customers.service.customer.commands.UpdateCustomerStatus;
import customers.web.DTO.Requests.Customer.DTOCustomerNewProfile;
import customers.web.DTO.Requests.Customer.DTOCustomerUpdateCredentials;
import customers.web.DTO.Requests.Customer.DTOCustomerUpdateProfile;
import customers.web.DTO.Responses.Customer.DTOCustomerProfileCreatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerCommandHandler {

    // == Fields ==
    private final CreateNewCustomer createNewCustomer;
    private final UpdateCustomerInformation updateCustomerInformation;
    private final UpdateCustomerCredentials updateCustomerCredentials;
    private final UpdateCustomerStatus updateCustomerStatus;

    // == Constructors ==
    @Autowired
    public CustomerCommandHandler(CreateNewCustomer createNewCustomer, UpdateCustomerInformation updateCustomerInformation, UpdateCustomerCredentials updateCustomerCredentials, UpdateCustomerStatus updateCustomerStatus) {
        this.createNewCustomer = createNewCustomer;
        this.updateCustomerInformation = updateCustomerInformation;
        this.updateCustomerCredentials = updateCustomerCredentials;
        this.updateCustomerStatus = updateCustomerStatus;
    }

    @Transactional
    public DTOCustomerProfileCreatedResponse createCustomer(DTOCustomerNewProfile dto){
        return createNewCustomer.handle(dto);
    }

    @Transactional
    public void updateCustomer(UUID customerId, DTOCustomerUpdateProfile dto){
        updateCustomerInformation.handle(customerId, dto);
    }

    @Transactional
    public void updateCredentials(UUID customerId, DTOCustomerUpdateCredentials dto){
        updateCustomerCredentials.handle(customerId, dto);
    }

    @Transactional
    public void updateAccountStatus(UUID customerId, AccountStatus status){
        updateCustomerStatus.handle(customerId, status);
    }
}
