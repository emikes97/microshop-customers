package customers.web.DTO.Responses;

import java.util.UUID;

public record DTOCustomerProfileCreatedResponse(UUID customerId, String username, String customerName) {
}
