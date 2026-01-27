package customers.web.DTO.Responses;


public record DTOCustomerProfileResponse(String username,
                                         String customerName,
                                         String customerSurname,
                                         String email,
                                         String phoneNumber) {
}
