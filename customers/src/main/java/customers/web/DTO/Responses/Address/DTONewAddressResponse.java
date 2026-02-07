package customers.web.DTO.Responses.Address;

public record DTONewAddressResponse(
        String country,
        String city,
        String street,
        String postalCode) {}
