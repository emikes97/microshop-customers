package customers.web.controllers;

import customers.domain.model.CustomerAddress;
import customers.service.address.handlers.AddressCommandHandler;
import customers.service.address.handlers.AddressQueryHandler;
import customers.web.DTO.PageResult.PageResult;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressNewAddress;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressUpdateAddress;
import customers.web.DTO.Responses.Address.DTONewAddressResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/microshop/v1/customers/{customerId}/addresses")
public class CustomerAddressController {

    // == Fields ==
    private final AddressCommandHandler command;
    private final AddressQueryHandler query;

    // == Constructors ==
    @Autowired
    public CustomerAddressController(AddressCommandHandler command, AddressQueryHandler query) {
        this.command = command;
        this.query = query;
    }

    // == Public Methods ==

    /// == Post == ///
    /*
       curl -i -X POST "http://localhost:8081/microshop/v1/customers/a4e8e26c-32eb-42ed-8a59-1bdeaec86818/addresses" \
      -H "Content-Type: application/json" \
      -d '{
        "country": "Greece",
        "city": "Athens",
        "street": "Ermou 1",
        "postalCode": "10563",
        "isDefault": true
      }'
     */
    @PostMapping
    public DTONewAddressResponse createNewAddress(@PathVariable UUID customerId, @Valid @RequestBody DTOCustomerAddressNewAddress dto){
        return command.createNewAddress(customerId, dto);
    }

    /// == Put == || == Patch == ///
    /*
        curl -i -X PUT "http://localhost:8081/microshop/v1/customers/a4e8e26c-32eb-42ed-8a59-1bdeaec86818/addresses/1" \
      -H "Content-Type: application/json" \
      -d '{
        "country": "Greece",
        "city": "Athens",
        "street": "Stadiou 10",
        "postalCode": "10564",
        "isDefault": false
      }'
     */
    @PutMapping("/{addressId}")
    public void updateExistingAddress(@PathVariable UUID customerId, @PathVariable long addressId, @RequestBody DTOCustomerAddressUpdateAddress dto){
        command.updateExistingAddress(customerId, addressId, dto);
    }

    /// curl -i -X PUT "http://localhost:8081/microshop/v1/customers/a4e8e26c-32eb-42ed-8a59-1bdeaec86818/addresses/1/default"
    @PutMapping("/{addressId}/default")
    public void setNewDefaultAddress(@PathVariable UUID customerId, @PathVariable long addressId){
        command.setNewDefaultAddress(customerId, addressId);
    }

    /// == Delete == ///
    /// curl -i -X DELETE "http://localhost:8081/microshop/v1/customers/a4e8e26c-32eb-42ed-8a59-1bdeaec86818/addresses/1"
    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable UUID customerId, @PathVariable long addressId){
        command.deleteAddress(customerId, addressId);
    }

    /// curl -i -X DELETE "http://localhost:8081/microshop/v1/customers/a4e8e26c-32eb-42ed-8a59-1bdeaec86818/addresses/default"
    @DeleteMapping("/default")
    public void removeDefaultAddress(@PathVariable UUID customerId){
        command.removeDefaultAddress(customerId);
    }


    /// == Get == ///
    /// curl -i -X GET "http://localhost:8081/microshop/v1/customers/a4e8e26c-32eb-42ed-8a59-1bdeaec86818/addresses/1"
    @GetMapping("/{addressId}")
    public DTONewAddressResponse getCustomerAddress(@PathVariable UUID customerId, @PathVariable long addressId){
        return query.getAddress(customerId, addressId);
    }

    /// curl -i -X GET "http://localhost:8081/microshop/v1/customers/a4e8e26c-32eb-42ed-8a59-1bdeaec86818/addresses?limit=10&offset=0"
    @GetMapping
    public PageResult<CustomerAddress> getAllCustomerAddresses(@PathVariable UUID customerId, @RequestParam int limit, @RequestParam int offset){
        return query.getPagedAddresses(customerId, limit, offset);
    }
}
