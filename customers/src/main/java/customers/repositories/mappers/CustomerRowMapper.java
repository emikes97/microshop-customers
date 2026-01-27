package customers.repositories.mappers;

import customers.domain.model.Customer;
import customers.domain.state.AccountStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Customer(
                rs.getObject("customer_id", UUID.class),
                rs.getString("username"),
                rs.getString("password_hash"),
                rs.getString("phone_number"),
                rs.getString("email"),
                rs.getString("customer_name"),
                rs.getString("customer_surname"),
                rs.getObject("subscribed_at", OffsetDateTime.class),
                rs.getObject("subscribed_valid_until_at", OffsetDateTime.class),
                rs.getInt("version"),
                AccountStatus.valueOf(rs.getString("status")),
                rs.getObject("created_at", OffsetDateTime.class),
                rs.getObject("updated_at", OffsetDateTime.class),
                rs.getObject("password_changed_at", OffsetDateTime.class),
                rs.getObject("deleted_at", OffsetDateTime.class)
        );
    }
}
