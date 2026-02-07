package customers.service.address.commands;

import customers.service.address.writer.AddressWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateDefault {

    // == Fields ==
    private final AddressWriter writer;

    // == Constructors ==
    @Autowired
    public UpdateDefault(AddressWriter writer) {
        this.writer = writer;
    }

    // == Public Methods ==

    public void removeDefault(UUID customerId){
        writer.setDefaultToFalse(customerId);
    }

    public void setNewDefault(UUID customerId, long addressId){
        writer.setDefaultToFalse(customerId);
        if(!writer.setNewDefaultAddress(customerId, addressId))
            throw new IllegalStateException("Updating address " + addressId + " failed");
    }
}
