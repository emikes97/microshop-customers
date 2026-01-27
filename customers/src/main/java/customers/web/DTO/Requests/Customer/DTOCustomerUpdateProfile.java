package customers.web.DTO.Requests.Customer;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record DTOCustomerUpdateProfile(@Nullable @Size(min = 4, max = 100) String username,
                                       @Nullable @Size(max = 25) String phoneNumber,
                                       @Nullable @Size(max = 255) @Email String email,
                                       @Nullable @Size(max = 255) String customerName,
                                       @Nullable @Size(max = 255) String customerSurname) {
}
