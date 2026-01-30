package customers.repositories.impl;

import customers.domain.model.CustomerAddress;
import customers.repositories.CustomerAddressRepository;
import customers.repositories.sql.CustomerAddressSql;
import customers.web.DTO.PageResult.PageResult;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressNewAddress;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressUpdateAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JdbcCustomerAddressRepository implements CustomerAddressRepository {

    // == Fields ==
    private final JdbcTemplate jdbc;
    private final RowMapper<CustomerAddress> mapper;

    // == Constructors ==
    @Autowired
    public JdbcCustomerAddressRepository(JdbcTemplate jdbc, RowMapper<CustomerAddress> mapper) {
        this.jdbc = jdbc;
        this.mapper = mapper;
    }

    // == Public Methods ==

    @Override
    public CustomerAddress insertNewAddress(DTOCustomerAddressNewAddress dto) {

        CustomerAddress inserted = jdbc.queryForObject(CustomerAddressSql.INSERT_CUSTOMER_ADDRESS,
                mapper,
                dto.customerId(),
                dto.country(),
                dto.city(),
                dto.street(),
                dto.postalCode(),
                dto.isDefault());

        if(inserted == null)
            throw new IllegalStateException("Insert returned null for customerId: " + dto.customerId());

        return inserted;
    }

    @Override
    public boolean updateExistingAddress(DTOCustomerAddressUpdateAddress dto, long addressId, UUID customerId, int expectedVersion) {
        int row = jdbc.update(CustomerAddressSql.UPDATE_CUSTOMER_ADDRESS,
                dto.country(),
                dto.city(),
                dto.street(),
                dto.postalCode(),
                addressId,
                customerId,
                expectedVersion);

        return row == 1;
    }

    @Override
    public boolean setNewDefaultAddress(UUID customerId, long addressId) {
        int row = jdbc.update(CustomerAddressSql.SET_CUSTOMER_ADDRESS_TO_DEFAULT,
                addressId,
                addressId,
                addressId,
                customerId,
                addressId);

        return row == 1;
    }

    @Override
    public boolean deleteAddress(UUID customerId, long addressId, int expectedVersion) {
        int row = jdbc.update(CustomerAddressSql.DELETE_CUSTOMER_ADDRESS, customerId, addressId, expectedVersion);
        return row == 1;
    }

    @Override
    public CustomerAddress getCustomerAddress(UUID customerId, long addressId) {
        return jdbc.queryForObject(CustomerAddressSql.FIND_CUSTOMER_ADDRESS_BY_ID, mapper, addressId, customerId);
    }

    @Override
    public PageResult<CustomerAddress> getPagedCustomerAddresses(UUID customerId, int limit, int offset) {
        List<CustomerAddress> content = jdbc.query(CustomerAddressSql.GET_ALL_CUSTOMER_ADDRESSES, mapper, customerId, limit, offset);
        Long total = jdbc.queryForObject(CustomerAddressSql.GET_COUNT_OF_ALL_CUSTOMER_ADDRESSES, Long.class, customerId);

        long totalElements = (total == null) ? 0L : total;
        int page = (limit > 0) ? (offset / limit) : 0;

        return new PageResult<>(content, page, limit, totalElements);
    }

    @Override
    public boolean setDefaultToFalse(UUID customerId) {

        int row = jdbc.update(CustomerAddressSql.SET_DEFAULT_ADDRESS_TO_FALSE, customerId);

        return row == 1;
    }

    @Override
    public boolean setNewDefault(UUID customerId, long addressId, int expectedVersion) {

        int row = jdbc.update(CustomerAddressSql.SET_ADDRESS_TO_DEFAULT, customerId, addressId, expectedVersion);

        return row == 1;
    }
}
