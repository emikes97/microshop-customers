package customers.repositories;

import customers.domain.model.CustomerPaymentMethod;
import customers.domain.state.TokenStatus;
import customers.web.DTO.PageResult.PageResult;

import java.util.Optional;
import java.util.UUID;

public interface PaymentMethodRepository {

   public CustomerPaymentMethod insertNewPaymentMethod(CustomerPaymentMethod paymentMethod);
   public Optional<CustomerPaymentMethod> findById(UUID customerId, UUID paymentId);
   public PageResult<CustomerPaymentMethod> getAllPagedPaymentMethods(UUID customerId, int limit, int offset);
   public boolean replaceExistingPaymentMethod(UUID customerId, UUID paymentId, int expectedVersion, CustomerPaymentMethod paymentMethod);
   public boolean updateRefToken(UUID customerId, UUID paymentId, int expectedVersion, String refToken);
   public boolean updateMethodStatus(UUID customerId, UUID paymentId, int expectedVersion, TokenStatus status);
   public boolean deletePaymentMethod(UUID customerId, UUID paymentId, int expectedVersion);
   public boolean removeDefault(UUID customerId);
   public boolean setNewDefault(UUID customerId, UUID paymentId);
}
