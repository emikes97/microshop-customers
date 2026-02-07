package customers.service.address.queries;

import customers.domain.model.CustomerAddress;
import customers.repositories.CustomerAddressRepository;
import customers.service.address.factory.AddressFactory;
import customers.web.DTO.PageResult.PageResult;
import customers.web.DTO.Responses.Address.DTONewAddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressQueries {

    // == Fields ==
    private final CustomerAddressRepository repo;
    private final AddressFactory factory;

    // == Constructors ==
    @Autowired
    public AddressQueries(CustomerAddressRepository repo, AddressFactory factory) {
        this.repo = repo;
        this.factory = factory;
    }

    // == Public Methods ==
    public DTONewAddressResponse getAddress(UUID customerId, long addressId){
        CustomerAddress address = repo.getCustomerAddress(customerId, addressId);
        return factory.createResponse(address.getCountry(), address.getCity(), address.getStreet(), address.getPostalCode());
    }

    public PageResult<CustomerAddress> getAllAddresses(UUID customerId, int limit, int offset){
        return repo.getPagedCustomerAddresses(customerId, limit, offset);
    }

    // == Internal ==
    public CustomerAddress getAddressEnt(UUID customerId, long addressId){
        return repo.getCustomerAddress(customerId, addressId);
    }
}
