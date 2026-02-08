package customers.repositories.mappers;

import customers.domain.model.CustomerPaymentMethod;
import customers.domain.state.TokenStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class PaymentMethodMapper  implements RowMapper<CustomerPaymentMethod> {

    @Override
    public CustomerPaymentMethod mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CustomerPaymentMethod(
                rs.getObject("customer_payment_id", UUID.class),
                rs.getObject("customer_id", UUID.class),
                rs.getString("provider"),
                rs.getString("brand"),
                rs.getString("payment_ref_token"),
                rs.getShort("exp_year"),
                rs.getShort("exp_month"),
                TokenStatus.valueOf(rs.getString("status")),
                rs.getBoolean("is_default"),
                rs.getInt("version"),
                rs.getObject("created_at", OffsetDateTime.class),
                rs.getObject("updated_at", OffsetDateTime.class)
        );
    }
}
