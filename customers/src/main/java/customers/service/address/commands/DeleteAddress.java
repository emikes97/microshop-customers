package customers.service.address.commands;

import customers.domain.model.CustomerAddress;
import customers.service.address.queries.AddressQueries;
import customers.service.address.writer.AddressWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteAddress {

    // == Fields ==
    private final AddressWriter writer;
    private final AddressQueries queries;

    // == Constructors ==
    @Autowired
    public DeleteAddress(AddressWriter writer, AddressQueries queries) {
        this.writer = writer;
        this.queries = queries;
    }

    // == Public Methods ==

    public void handle(UUID customerId, long addressId){
        CustomerAddress addressToDelete = queries.getAddress(customerId, addressId);
        if(!writer.deleteAddress(customerId, addressId, addressToDelete.getVersion()))
            throw new IllegalStateException("Stale version: address changed by another request");
    }
}
