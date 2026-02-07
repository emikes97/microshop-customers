package customers.web.DTO.Requests.CustomerAddress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record DTOCustomerAddressNewAddress(
        @Size(max = 150) @NotNull @NotBlank String country,
        @Size(max = 150) @NotNull @NotBlank String city,
        @Size(max = 150) @NotNull @NotBlank String street,
        @Size(max = 50) @NotNull @NotBlank String postalCode,
        boolean isDefault) {}