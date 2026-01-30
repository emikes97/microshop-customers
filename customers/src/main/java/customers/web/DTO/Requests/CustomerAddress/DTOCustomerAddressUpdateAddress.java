package customers.web.DTO.Requests.CustomerAddress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DTOCustomerAddressUpdateAddress(
        @Size(max = 150) @NotNull @NotBlank String country,
        @Size(max = 150) @NotNull @NotBlank String city,
        @Size(max = 150) @NotNull @NotBlank String street,
        @Size(max = 50) @NotNull @NotBlank String postalCode
) {}


///            country = ?,
//            city = ?,
//            street = ?,
//            postal_code = ?,
//            version = version + 1,
//            updated_at = now()
//            WHERE address_id = ?
//            AND customer_id = ?
//            AND version = ?