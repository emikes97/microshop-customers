package customers.service.customer.factory;

import customers.domain.model.Customer;
import customers.web.DTO.Requests.Customer.DTOCustomerNewProfile;
import customers.web.DTO.Requests.Customer.DTOCustomerUpdateProfile;
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

    public Customer updateCustomer(Customer customer, DTOCustomerUpdateProfile dto){
        return new Customer(customer.getCustomerId(),
                keepOldIfBlank(dto.username(), customer.getUsername()),
                customer.getPasswordHash(),
                keepOldIfBlank(dto.phoneNumber(), customer.getPhoneNumber()),
                keepOldIfBlank(dto.email(), customer.getEmail()),
                keepOldIfBlank(dto.customerName(), customer.getCustomerName()),
                keepOldIfBlank(dto.customerSurname(), customer.getCustomerSurname()),
                customer.getSubscribedAt(),
                customer.getSubscribedValidUntilAt(),
                customer.getVersion(),
                customer.getStatus(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getPasswordChangedAt(),
                customer.getDeletedAt());
    }
    
    private String keepOldIfBlank(String incoming, String current){
        if(incoming == null) return current;
        if(incoming.trim().isBlank()) return current;
        return incoming.trim();
    }
}
