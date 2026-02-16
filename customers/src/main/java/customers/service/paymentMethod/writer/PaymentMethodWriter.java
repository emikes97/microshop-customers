package customers.service.paymentMethod.writer;

import customers.domain.model.CustomerPaymentMethod;
import customers.repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodWriter {

    // == Fields ==
    private final PaymentMethodRepository repo;

    // == Constructors ==
    @Autowired
    public PaymentMethodWriter(PaymentMethodRepository repo) {
        this.repo = repo;
    }

    // == Public Methods ==

    public CustomerPaymentMethod insert(CustomerPaymentMethod customerPaymentMethod){
        return repo.insertNewPaymentMethod(customerPaymentMethod);
    }

}
