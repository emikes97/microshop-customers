package customers.service.address.handlers;

import customers.domain.model.CustomerAddress;
import customers.service.address.queries.AddressQueries;
import customers.web.DTO.PageResult.PageResult;
import customers.web.DTO.Responses.Address.DTONewAddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AddressQueryHandler {

    // == Fields ==
    private final AddressQueries queries;

    // == Constructors ==
    @Autowired
    public AddressQueryHandler(AddressQueries queries) {
        this.queries = queries;
    }

    // == Public Methods ==
    @Transactional(readOnly = true)
    public DTONewAddressResponse getAddress(UUID customerId, long addressId){
        return queries.getAddress(customerId, addressId);
    }

    @Transactional(readOnly = true)
    public PageResult<CustomerAddress> getPagedAddresses(UUID customerId, int limit, int offset){
        return queries.getAllAddresses(customerId, limit, offset);
    }
}
