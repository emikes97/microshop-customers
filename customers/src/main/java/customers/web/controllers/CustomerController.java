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
    @PostMapping
    public ResponseEntity<DTOCustomerProfileCreatedResponse> createNewCustomer(@RequestBody @Valid DTOCustomerNewProfile dto){
        DTOCustomerProfileCreatedResponse response = customerCommandHandler.createCustomer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // Requires Authorization
    public void updateCustomer(@PathVariable UUID customerId, @RequestBody @Valid DTOCustomerUpdateProfile dto){
        customerCommandHandler.updateCustomer(customerId, dto);
    }

    @PatchMapping("/{customerId}/credentials")
    @ResponseStatus(HttpStatus.ACCEPTED)
    // Requires Authorization
    public void updateCredentials(@PathVariable UUID customerId, @RequestBody @Valid DTOCustomerUpdateCredentials dto){
        customerCommandHandler.updateCredentials(customerId, dto);
    }

    @GetMapping("/search")
    // Requires Authorization
    public DTOCustomerProfileResponse searchCustomerProfile(@RequestParam(required = false) UUID customerId,
                                                         @RequestParam(required = false) String email,
                                                         @RequestParam(required = false) String phoneNumber,
                                                         @RequestParam(required = false) String username){
        return customerQueryHandler.searchCustomer(customerId, email, phoneNumber, username);
    }

    @GetMapping("/{customerId}")
    public DTOCustomerProfileResponse getCustomerById(@PathVariable UUID customerId){
        return customerQueryHandler.findCustomer(customerId);
    }
}
