package customers.web.DTO.Requests.Customer;

import jakarta.validation.constraints.*;

public record DTOCustomerNewProfile(@Size(min = 4, max = 100) @NotBlank String username,
                                    @Size(min = 10, max = 255) @NotBlank String password,
                                    @Size(max = 25) @NotBlank String phoneNumber,
                                    @Size(max = 255) @NotBlank @Email String email,
                                    @Size(max = 255) @NotBlank String customerName,
                                    @Size(max = 255) @NotBlank String customerSurname) {}