package customers.service.customer.commands;

import customers.domain.model.Customer;
import customers.service.customer.handlers.CustomerQueryHandler;
import customers.service.customer.writer.CustomerWriter;
import customers.web.DTO.Requests.Customer.DTOCustomerUpdateCredentials;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateCustomerCredentials {

    // == Fields ==
    private final CustomerQueryHandler customerQueryHandler;
    private final CustomerWriter customerWriter;
    private final PasswordEncoder encoder;

    // == Constructors ==
    @Autowired
    public UpdateCustomerCredentials(CustomerQueryHandler customerQueryHandler, CustomerWriter customerWriter, PasswordEncoder encoder) {
        this.customerQueryHandler = customerQueryHandler;
        this.customerWriter = customerWriter;
        this.encoder = encoder;
    }

    // == Public Methods ==
    public void handle(UUID customerId, DTOCustomerUpdateCredentials dto) {

        Customer customer = customerQueryHandler.getCustomer(customerId);

        if(!encoder.matches(dto.oldPassword(), customer.getPasswordHash()))
            throw new BadCredentialsException("Error: Invalid current password");

        if(encoder.matches(dto.newPassword(), customer.getPasswordHash()))
            throw new ValidationException("Error: New password must be different");

        String newHashedPassword = encoder.encode(dto.newPassword());

        if(!customerWriter.updateCredentials(customerId, newHashedPassword, customer.getVersion()))
            throw new IllegalStateException("Stale version: customer was updated by another request");
    }
}
