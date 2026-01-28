package customers.web.DTO.Responses.Customer;

import java.util.UUID;

public record DTOCustomerProfileCreatedResponse(UUID customerId, String username, String customerName) {
}
