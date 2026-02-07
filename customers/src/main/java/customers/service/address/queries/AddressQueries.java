package customers.service.address.queries;

import customers.domain.model.CustomerAddress;
import customers.repositories.CustomerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressQueries {

    // == Fields ==
    private final CustomerAddressRepository repo;

    // == Constructors ==
    @Autowired
    public AddressQueries(CustomerAddressRepository repo) {
        this.repo = repo;
    }

    // == Public Methods ==
    public CustomerAddress getAddress(UUID customerId, long addressId){
        return repo.getCustomerAddress(customerId, addressId);
    }
}
