package customers.repositories.mappers;

import customers.domain.model.CustomerAddress;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class CustomerAddressMapper implements RowMapper<CustomerAddress> {

    @Override
    public CustomerAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CustomerAddress(
                rs.getLong("address_id"),
                rs.getObject("customer_id", UUID.class),
                rs.getString("country"),
                rs.getString("city"),
                rs.getString("street"),
                rs.getString("postal_code"),
                rs.getBoolean("is_default"),
                rs.getInt("version"),
                rs.getObject("created_at", OffsetDateTime.class),
                rs.getObject("updated_at", OffsetDateTime.class)
        );
    }
}