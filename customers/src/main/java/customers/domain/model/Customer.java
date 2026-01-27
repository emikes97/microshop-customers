package customers.domain.model;

import customers.domain.state.AccountStatus;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class Customer {

    // == Fields ==
    private final UUID customerId;
    private final String username;
    private final String passwordHash;
    private final String phoneNumber;
    private final String email;
    private final String customerName;
    private final String customerSurname;
    private final OffsetDateTime subscribedAt;
    private final OffsetDateTime subscribedValidUntilAt;
    private final int version;
    private final AccountStatus status;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
    private final OffsetDateTime passwordChangedAt;
    private final OffsetDateTime deletedAt;

    // == Constructors ==
    public Customer(UUID customerId, String username, String passwordHash, String phoneNumber, String email,
                    String customerName, String customerSurname, OffsetDateTime subscribedAt, OffsetDateTime subscribedValidUntilAt,
                    int version, AccountStatus status, OffsetDateTime createdAt, OffsetDateTime updatedAt,
                    OffsetDateTime passwordChangedAt, OffsetDateTime deletedAt) {

        this.customerId = customerId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.subscribedAt = subscribedAt;
        this.subscribedValidUntilAt = subscribedValidUntilAt;
        this.version = version;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.passwordChangedAt = passwordChangedAt;
        this.deletedAt = deletedAt;
    }

    public Customer(UUID customerId, String username, String passwordHash, String phoneNumber, String email, String customerName, String customerSurname){
        this(customerId, username, passwordHash, phoneNumber, email, customerName, customerSurname, null, null, 0,
                AccountStatus.CREATED, null, null, null, null);
    }

    // == Public Methods ==

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", username='" + username + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerSurname='" + customerSurname + '\'' +
                ", version=" + version +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
