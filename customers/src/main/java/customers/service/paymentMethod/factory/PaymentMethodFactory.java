package customers.service.paymentMethod.factory;

import customers.domain.model.CustomerPaymentMethod;
import customers.web.DTO.Requests.PaymentMethod.DTOCreateNewPaymentMethod;
import customers.web.DTO.Responses.PaymentMethod.DTONewPaymentMethodResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMethodFactory {

    public CustomerPaymentMethod create(UUID customerId, DTOCreateNewPaymentMethod dto){
        return new CustomerPaymentMethod(UUID.randomUUID(), customerId, dto.provider(), dto.brand(), dto.paymentRefToken(), dto.expYear(), dto.expMonth(), dto.isDefault());
    }

    public DTONewPaymentMethodResponse createResponse(CustomerPaymentMethod method){
        return new DTONewPaymentMethodResponse(method.getProvider(), method.getBrand(), method.getCreatedAt());
    }
}
