package customers.repositories;

import customers.domain.model.CustomerAddress;
import customers.web.DTO.PageResult.PageResult;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressNewAddress;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressUpdateAddress;

import java.util.UUID;

public interface CustomerAddressRepository {

    // SQL CRUD Actions
    public CustomerAddress insertNewAddress(DTOCustomerAddressNewAddress dto);
    public boolean updateExistingAddress(DTOCustomerAddressUpdateAddress dto, long addressId, UUID customerId, int expectedVersion);
    public boolean setNewDefaultAddress(UUID customerId, long addressId);
    public boolean deleteAddress(UUID customerId, long addressId, int expectedVersion);

    // SQL queries
    public CustomerAddress getCustomerAddress(UUID customerId, long addressId);
    public PageResult<CustomerAddress> getPagedCustomerAddresses(UUID customerId, int limit, int offset);

}
