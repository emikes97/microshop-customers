package customers.service.address.commands;

import customers.domain.model.CustomerAddress;
import customers.service.address.factory.AddressFactory;
import customers.service.address.queries.AddressQueries;
import customers.service.address.writer.AddressWriter;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressUpdateAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateAddress {

    // == Fields ==
    private final AddressFactory factory;
    private final AddressWriter writer;
    private final AddressQueries queries;

    // == Constructors ==
    @Autowired
    public UpdateAddress(AddressFactory factory, AddressWriter writer, AddressQueries queries) {
        this.factory = factory;
        this.writer = writer;
        this.queries = queries;
    }

    // == Public Methods ==
    public void handle(UUID customerId, long addressId, DTOCustomerAddressUpdateAddress dto){
        if(noOperation(dto)) return;
        CustomerAddress existingAddress = queries.getAddress(customerId, addressId);
        CustomerAddress updatedAddress = factory.update(existingAddress, dto);
        if(!writer.update(updatedAddress, updatedAddress.getAddressId(), customerId, updatedAddress.getVersion()))
            throw new IllegalStateException("Stale version: customer was updated by another request");
    }

    private boolean noOperation(DTOCustomerAddressUpdateAddress dto){
        return ((dto.country() == null || dto.country().trim().isBlank()) &&
                (dto.city() == null || dto.city().trim().isBlank()) &&
                (dto.street() == null || dto.street().trim().isBlank()) &&
                (dto.postalCode() == null || dto.postalCode().trim().isBlank()));
    }
}
