package customers.domain.model;

import customers.domain.state.TokenStatus;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class CustomerPaymentMethod {

    // == Fields ==
    private final UUID customerPaymentId;
    private final UUID customerId;
    private final String provider;
    private final String brand;
    private final String paymentRefToken;
    private final short expYear;
    private final short expMonth;
    private final TokenStatus status;
    private final boolean isDefault;
    private final int version;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    // == Constructors ==

    public CustomerPaymentMethod(UUID customerPaymentId, UUID customerId, String provider, String brand,
                                 String paymentRefToken, short expYear, short expMonth, TokenStatus status,
                                 boolean isDefault, int version, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.customerPaymentId = customerPaymentId;
        this.customerId = customerId;
        this.provider = provider;
        this.brand = brand;
        this.paymentRefToken = paymentRefToken;
        this.expYear = expYear;
        this.expMonth = expMonth;
        this.status = status;
        this.isDefault = isDefault;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // == Public Methods ==

    @Override
    public String toString() {
        return "CustomerPaymentMethod{" +
                "provider='" + provider + '\'' +
                ", customerId=" + customerId +
                ", brand='" + brand + '\'' +
                ", expYear=" + expYear +
                ", expMonth=" + expMonth +
                ", status=" + status +
                ", isDefault=" + isDefault +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}