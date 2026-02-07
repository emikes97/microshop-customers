package customers.repositories;

import customers.domain.model.CustomerAddress;
import customers.web.DTO.PageResult.PageResult;

import java.util.UUID;

public interface CustomerAddressRepository {

    // SQL CRUD Actions
    public CustomerAddress insertNewAddress(CustomerAddress address);
    public boolean updateExistingAddress(CustomerAddress address, long addressId, UUID customerId, int expectedVersion);
    public boolean setNewDefaultAddress(UUID customerId, long addressId); // will probably be deleted
    public boolean deleteAddress(UUID customerId, long addressId, int expectedVersion);
    public boolean setDefaultToFalse(UUID customerId);
    public boolean setNewDefault(UUID customerId, long addressId);

    // SQL queries
    public CustomerAddress getCustomerAddress(UUID customerId, long addressId);
    public PageResult<CustomerAddress> getPagedCustomerAddresses(UUID customerId, int limit, int offset);

}
