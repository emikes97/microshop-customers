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

        String newCustomerName = "";
        String newCustomerSurname = "";
        String newEmail = "";
        String newPhoneNumber = "";
        String newUserName = "";

        if(dto.customerName() != null){
            newCustomerName = dto.customerName().trim();
        }
        if(dto.customerSurname() != null){
            newCustomerSurname = dto.customerSurname().trim();
        }
        if(dto.email() != null){
            newEmail = dto.email().trim();
        }
        if(dto.phoneNumber() != null){
            newPhoneNumber = dto.phoneNumber().trim();
        }
        if(dto.username() != null){
            newUserName = dto.username().trim();
        }

        return new Customer(customer.getCustomerId(),
                newUserName.isBlank() ? customer.getUsername() : newUserName,
                customer.getPasswordHash(),
                newPhoneNumber.isBlank() ? customer.getPhoneNumber() : newPhoneNumber,
                newEmail.isBlank() ? customer.getEmail() : newEmail,
                newCustomerName.isBlank() ? customer.getCustomerName() : newCustomerName,
                newCustomerSurname.isBlank() ? customer.getCustomerSurname() : newCustomerSurname);
    }
}
