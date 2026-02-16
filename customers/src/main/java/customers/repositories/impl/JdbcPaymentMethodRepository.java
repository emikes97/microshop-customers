package customers.repositories.impl;

import customers.domain.model.CustomerPaymentMethod;
import customers.domain.state.TokenStatus;
import customers.repositories.PaymentMethodRepository;
import customers.repositories.sql.PaymentMethodSql;
import customers.web.DTO.PageResult.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcPaymentMethodRepository implements PaymentMethodRepository {

    // == Fields ==
    private final JdbcTemplate jdbc;
    private final RowMapper<CustomerPaymentMethod> mapper;

    // == Constructors ==
    @Autowired
    public JdbcPaymentMethodRepository(JdbcTemplate jdbc, RowMapper<CustomerPaymentMethod> mapper) {
        this.jdbc = jdbc;
        this.mapper = mapper;
    }

    // == Public Methods ==

    @Override
    public CustomerPaymentMethod insertNewPaymentMethod(CustomerPaymentMethod paymentMethod) {
        CustomerPaymentMethod inserted = jdbc.queryForObject(PaymentMethodSql.INSERT_NEW_PAYMENT_METHOD, mapper,
                paymentMethod.getCustomerPaymentId(),
                paymentMethod.getCustomerId(),
                paymentMethod.getProvider(),
                paymentMethod.getBrand(),
                paymentMethod.getPaymentRefToken(),
                paymentMethod.getExpYear(),
                paymentMethod.getExpMonth());

        if (inserted == null){
            throw new IllegalStateException("Insert returned null for customer: " + paymentMethod.getCustomerId());
        }

        return inserted;
    }

    @Override
    public Optional<CustomerPaymentMethod> findById(UUID customerId, UUID paymentId) {
        try {
            return Optional.ofNullable(jdbc.queryForObject(PaymentMethodSql.FIND_PAYMENT_METHOD, mapper, customerId, paymentId));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public PageResult<CustomerPaymentMethod> getAllPagedPaymentMethods(UUID customerId, int limit, int offset) {
        List<CustomerPaymentMethod> content = jdbc.query(PaymentMethodSql.GET_ALL_PAGED_PAYMENT_METHODS_FOR_CUSTOMER, mapper, customerId, limit, offset);
        Long total = jdbc.queryForObject(PaymentMethodSql.GET_COUNT_OF_ALL_CUSTOMER_PAYMENT_METHODS, Long.class, customerId);

        long totalElements = (total == null) ? 0L : total;
        int page = (limit > 0) ? (offset / limit) : 0;

        return new PageResult<>(content, page, limit, totalElements);
    }

    @Override
    public boolean replaceExistingPaymentMethod(UUID customerId, UUID paymentId, int expectedVersion, CustomerPaymentMethod paymentMethod) {

        int rows = jdbc.update(PaymentMethodSql.REPLACE_EXISTING_PAYMENT_METHOD,
                paymentMethod.getProvider(),
                paymentMethod.getBrand(),
                paymentMethod.getPaymentRefToken(),
                paymentMethod.getExpYear(),
                paymentMethod.getExpMonth(),
                customerId,
                paymentId,
                expectedVersion);

        return rows == 1;
    }

    @Override
    public boolean updateRefToken(UUID customerId, UUID paymentId, int expectedVersion, String refToken) {

        int rows = jdbc.update(PaymentMethodSql.UPDATE_REF_TOKEN,
                refToken,
                customerId,
                paymentId,
                expectedVersion);

        return rows == 1;
    }

    @Override
    public boolean updateMethodStatus(UUID customerId, UUID paymentId, int expectedVersion, TokenStatus status) {

        int rows = jdbc.update(PaymentMethodSql.UPDATE_METHOD_STATUS,
                status,
                customerId,
                paymentId,
                expectedVersion);

        return rows == 1;
    }

    @Override
    public boolean deletePaymentMethod(UUID customerId, UUID paymentId, int expectedVersion) {

        int deleted = jdbc.update(PaymentMethodSql.DELETE_PAYMENT_METHOD,
                customerId,
                paymentId,
                expectedVersion);

        return deleted == 1;
    }

    @Override
    public boolean removeDefault(UUID customerId) {

        int rows = jdbc.update(PaymentMethodSql.REMOVE_DEFAULT_FROM_CUSTOMER,
                customerId);

        return rows <= 1;
    }

    @Override
    public boolean setNewDefault(UUID customerId, UUID paymentId) {

        int rows = jdbc.update(PaymentMethodSql.SET_NEW_DEFAULT_FROM_CUSTOMER,
                customerId,
                paymentId);

        return rows == 1;
    }
}
