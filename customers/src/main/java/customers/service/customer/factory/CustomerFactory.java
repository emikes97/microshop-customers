package customers.service.customer.factory;

import customers.domain.model.Customer;
import customers.web.DTO.Requests.Customer.DTOCustomerNewProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerFactory {

    // == Fields ==
    private final PasswordEncoder encoder;

    // == Constructors ==
    @Autowired
    public CustomerFactory(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    // == Public Methods ==

    public Customer create(DTOCustomerNewProfile dto){

        String hashedPassword = encoder.encode(dto.password());

        return new Customer(UUID.randomUUID(), dto.username(), hashedPassword, dto.phoneNumber(), dto.email(), dto.customerName(), dto.customerSurname());
    }
}
