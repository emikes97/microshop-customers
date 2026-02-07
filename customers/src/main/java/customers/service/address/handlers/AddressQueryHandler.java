package customers.service.address.handlers;

import customers.domain.model.CustomerAddress;
import customers.service.address.queries.AddressQueries;
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


}
