package customers.service.paymentMethod.commands;

import customers.domain.model.CustomerPaymentMethod;
import customers.service.paymentMethod.factory.PaymentMethodFactory;
import customers.service.paymentMethod.writer.PaymentMethodWriter;
import customers.web.DTO.Requests.PaymentMethod.DTOCreateNewPaymentMethod;
import customers.web.DTO.Responses.PaymentMethod.DTONewPaymentMethodResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateNewPaymentMethod {

    // == Fields ==
    private final PaymentMethodFactory factory;
    private final PaymentMethodWriter writer;

    // == Constructors ==
    @Autowired
    public CreateNewPaymentMethod(PaymentMethodFactory factory, PaymentMethodWriter writer) {
        this.factory = factory;
        this.writer = writer;
    }

    // == Public Methods ==

    public DTONewPaymentMethodResponse handle(UUID customerId, DTOCreateNewPaymentMethod dto){
        CustomerPaymentMethod toInsert = factory.create(customerId, dto);
        CustomerPaymentMethod saved = writer.insert(toInsert);
        return factory.createResponse(saved);
    }
}
