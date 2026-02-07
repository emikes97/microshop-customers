package customers.domain.model;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class CustomerAddress {

    // == Fields ==
    private final long addressId;
    private final UUID customerId;
    private final String country;
    private final String city;
    private final String street;
    private final String postalCode;
    private final boolean isDefault;
    private final int version;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    // == Constructors ==

    public CustomerAddress(long addressId, UUID customerId, String country, String city, String street,
                           String postalCode, boolean isDefault, int version,
                           OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.addressId = addressId;
        this.customerId = customerId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.isDefault = isDefault;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CustomerAddress(CustomerAddress address, String country, String city, String street, String postalCode){
        this(address.getAddressId(), address.getCustomerId(), country, city, street, postalCode, address.isDefault, address.getVersion(),
                address.getCreatedAt(), address.getUpdatedAt());
    }

    public CustomerAddress(UUID customerId, String country, String city, String street, String postalCode, boolean isDefault){
        this.addressId = 0;
        this.customerId = customerId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.isDefault = isDefault;
        this.version = 0;
        this.createdAt = null;
        this.updatedAt = null;
    }

    // == Public Methods ==

    @Override
    public String toString() {
        return "CustomerAddress{" +
                "addressId=" + addressId +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", customerId=" + customerId +
                '}';
    }
}