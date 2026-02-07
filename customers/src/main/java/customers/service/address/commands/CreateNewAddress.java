package customers.service.address.commands;

import customers.domain.model.CustomerAddress;
import customers.service.address.factory.AddressFactory;
import customers.service.address.writer.AddressWriter;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressNewAddress;
import customers.web.DTO.Responses.Address.DTONewAddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateNewAddress {

    // == Fields ==
    private final AddressWriter writer;
    private final AddressFactory factory;

    // == Constructors ==
    @Autowired
    public CreateNewAddress(AddressWriter writer, AddressFactory factory) {
        this.writer = writer;
        this.factory = factory;
    }

    // == Public Methods ==

    public DTONewAddressResponse handle(UUID customerId, DTOCustomerAddressNewAddress dto) {
        try {
            CustomerAddress addressToPersist = factory.create(customerId, dto);
            CustomerAddress savedAddress = writer.insert(addressToPersist);
            if (!dto.isDefault()) {
                return factory.createResponse(savedAddress.getCountry(), savedAddress.getCity(), savedAddress.getStreet(), savedAddress.getPostalCode());
            }
            writer.setDefaultToFalse(customerId);
            writer.setNewDefaultAddress(customerId, savedAddress.getAddressId());
            return factory.createResponse(savedAddress.getCountry(), savedAddress.getCity(), savedAddress.getStreet(), savedAddress.getPostalCode());
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }
}
