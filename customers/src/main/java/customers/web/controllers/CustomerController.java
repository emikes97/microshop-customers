package customers.web.controllers;

import customers.service.customer.CustomerService;
import customers.web.DTO.Requests.Customer.DTOCustomerNewProfile;
import customers.web.DTO.Responses.DTOCustomerProfileCreatedResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/microshop/v1/customers")
public class CustomerController {

    // == Fields ==
    private final CustomerService customerService;

    // == Constructors ==
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // == Public Methods / Exposed Endpoints ==
    @PostMapping
    public ResponseEntity<DTOCustomerProfileCreatedResponse> createNewCustomer(@RequestBody @Valid DTOCustomerNewProfile dto){
        return null;
    }
}
