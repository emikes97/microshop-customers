package customers.web.DTO.Responses.PaymentMethod;

import java.time.OffsetDateTime;

public record DTONewPaymentMethodResponse(String provider, String brand, OffsetDateTime createdAt) {
}
