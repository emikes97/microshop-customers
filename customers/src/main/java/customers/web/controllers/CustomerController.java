package customers.web.controllers;

import customers.service.customer.handlers.CustomerCommandHandler;
import customers.web.DTO.Requests.Customer.DTOCustomerNewProfile;
import customers.web.DTO.Requests.Customer.DTOCustomerUpdateProfile;
import customers.web.DTO.Responses.DTOCustomerProfileCreatedResponse;
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

    // == Constructors ==
    @Autowired
    public CustomerController(CustomerCommandHandler customerCommandHandler) {
        this.customerCommandHandler = customerCommandHandler;
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
}
