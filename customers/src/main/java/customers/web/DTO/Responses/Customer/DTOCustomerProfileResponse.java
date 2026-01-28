package customers.web.DTO.Responses.Customer;


public record DTOCustomerProfileResponse(String username,
                                         String customerName,
                                         String customerSurname,
                                         String email,
                                         String phoneNumber) {
}
