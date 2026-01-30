package customers.service.address.writer;

import customers.domain.model.CustomerAddress;
import customers.repositories.impl.JdbcCustomerAddressRepository;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressNewAddress;
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

    public CustomerAddress insert(UUID customerId, DTOCustomerAddressNewAddress dto){
        return repository.insertNewAddress(customerId, dto);
    }
}
