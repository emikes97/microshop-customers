package customers.web.DTO.Requests.PaymentMethod;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DTOCreateNewPaymentMethod(
        @Size(max = 50) @NotBlank String provider,
        @Size(max = 25) @NotBlank String brand,
        @Size(max = 255) @NotBlank String paymentRefToken,
        @Min(2026) @Max(2070) short expYear,
        @Min(1) @Max(12) short expMonth,
        boolean isDefault
        ){}
