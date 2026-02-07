package customers.service.address.factory;

import customers.domain.model.CustomerAddress;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressNewAddress;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressUpdateAddress;
import customers.web.DTO.Responses.Address.DTONewAddressResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressFactory {

    // == Public Methods ==
    public CustomerAddress create(UUID customerId, DTOCustomerAddressNewAddress address){
        return new CustomerAddress(customerId, address.country(), address.city(), address.street(), address.postalCode(), address.isDefault());
    }

    public DTONewAddressResponse createResponse(String country, String city, String street, String postalCode){
        return new DTONewAddressResponse(country, city, street, postalCode);
    }

    public CustomerAddress update(CustomerAddress address, DTOCustomerAddressUpdateAddress dto){
        return new CustomerAddress(address,
                keepOldIfBlank(dto.country(), address.getCountry()),
                keepOldIfBlank(dto.city(), address.getCity()),
                keepOldIfBlank(dto.street(), address.getStreet()),
                keepOldIfBlank(dto.postalCode(), address.getPostalCode()));
    }

    // == Private Methods ==
    private String keepOldIfBlank(String incoming, String current){
        if(incoming == null) return current;
        if(incoming.trim().isBlank()) return current;
        return incoming.trim();
    }
}
