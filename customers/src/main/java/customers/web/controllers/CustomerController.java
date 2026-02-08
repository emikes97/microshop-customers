package customers.web.controllers;

import customers.service.customer.handlers.CustomerCommandHandler;
import customers.service.customer.handlers.CustomerQueryHandler;
import customers.web.DTO.Requests.Customer.DTOCustomerNewProfile;
import customers.web.DTO.Requests.Customer.DTOCustomerUpdateCredentials;
import customers.web.DTO.Requests.Customer.DTOCustomerUpdateProfile;
import customers.web.DTO.Responses.Customer.DTOCustomerProfileCreatedResponse;
import customers.web.DTO.Responses.Customer.DTOCustomerProfileResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/microshop/v1/customers")
public class CustomerController {

    // == Fields ==
    private final CustomerCommandHandler customerCommandHandler;
    private final CustomerQueryHandler customerQueryHandler;

    // == Constructors ==
    @Autowired
    public CustomerController(CustomerCommandHandler customerCommandHandler, CustomerQueryHandler customerQueryHandler) {
        this.customerCommandHandler = customerCommandHandler;
        this.customerQueryHandler = customerQueryHandler;
    }

    // == Public Methods / Exposed Endpoints ==

    /// == Post == ///
    /*
        curl -i -X POST "http://localhost:8081/microshop/v1/customers" \
      -H "Content-Type: application/json" \
      -d '{
        "username": "mike",
        "email": "mike@example.com",
        "phoneNumber": "6912345678",
        "firstName": "Mike",
        "lastName": "Emmanouil",
        "password": "StrongPassword123!"
      }'
     */
    @PostMapping
    public ResponseEntity<DTOCustomerProfileCreatedResponse> createNewCustomer(@RequestBody @Valid DTOCustomerNewProfile dto){
        DTOCustomerProfileCreatedResponse response = customerCommandHandler.createCustomer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /// == Put == || == Patch == ///
    /*
        curl -i -X PATCH "http://localhost:8081/microshop/v1/customers/a4e8e26c-32eb-42ed-8a59-1bdeaec86818" \
      -H "Content-Type: application/json" \
      -d '{
        "username": "mike_new",
        "email": "mike_new@example.com",
        "phoneNumber": "6912345679",
        "firstName": "Mike",
        "lastName": "Emmanouil"
      }'
     */
    @PatchMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // Requires Authorization
    public void updateCustomer(@PathVariable UUID customerId, @RequestBody @Valid DTOCustomerUpdateProfile dto){
        customerCommandHandler.updateCustomer(customerId, dto);
    }

    /*
        curl -i -X PATCH "http://localhost:8081/microshop/v1/customers/a4e8e26c-32eb-42ed-8a59-1bdeaec86818/credentials" \
      -H "Content-Type: application/json" \
      -d '{
        "currentPassword": "StrongPassword123!",
        "newPassword": "EvenStrongerPassword456!"
      }'
     */
    @PatchMapping("/{customerId}/credentials")
    @ResponseStatus(HttpStatus.ACCEPTED)
    // Requires Authorization
    public void updateCredentials(@PathVariable UUID customerId, @RequestBody @Valid DTOCustomerUpdateCredentials dto){
        customerCommandHandler.updateCredentials(customerId, dto);
    }

    /// == Get == ///

    /// curl -i -X GET "http://localhost:8081/microshop/v1/customers/search?customerId=a4e8e26c-32eb-42ed-8a59-1bdeaec86818"
    /// curl -i -X GET "http://localhost:8081/microshop/v1/customers/search?email=mike@example.com"
    /// curl -i -X GET "http://localhost:8081/microshop/v1/customers/search?phoneNumber=6912345678"
    /// curl -i -X GET "http://localhost:8081/microshop/v1/customers/search?username=mike"
    @GetMapping("/search")
    // Requires Authorization
    public DTOCustomerProfileResponse searchCustomerProfile(@RequestParam(required = false) UUID customerId,
                                                         @RequestParam(required = false) String email,
                                                         @RequestParam(required = false) String phoneNumber,
                                                         @RequestParam(required = false) String username){
        return customerQueryHandler.searchCustomer(customerId, email, phoneNumber, username);
    }

    /// curl -i -X GET "http://localhost:8081/microshop/v1/customers/search?username=mike"
    @GetMapping("/{customerId}")
    public DTOCustomerProfileResponse getCustomerById(@PathVariable UUID customerId){
        return customerQueryHandler.findCustomer(customerId);
    }
}
