package customers.service.address.writer;

import customers.domain.model.CustomerAddress;
import customers.repositories.impl.JdbcCustomerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressWriter {

    // == Fields ==
    private final JdbcCustomerAddressRepository repository;

    // == Constructors ==
    @Autowired
    public AddressWriter(JdbcCustomerAddressRepository repository) {
        this.repository = repository;
    }

    // == Public Methods ==

    public CustomerAddress insert(CustomerAddress address){
        return repository.insertNewAddress(address);
    }

    public boolean update(CustomerAddress address, long addressId, UUID customerId, int expectedVersion){
        return repository.updateExistingAddress(address, addressId, customerId, expectedVersion);
    }

    public boolean setDefaultToFalse(UUID customerId){
        return repository.setDefaultToFalse(customerId);
    }

    public boolean setNewDefaultAddress(UUID customerId, long addressId){
        return repository.setNewDefault(customerId, addressId);
    }

    public boolean deleteAddress(UUID customerId, long addressId, int expectedVersion){
        return repository.deleteAddress(customerId, addressId, expectedVersion);
    }
}
