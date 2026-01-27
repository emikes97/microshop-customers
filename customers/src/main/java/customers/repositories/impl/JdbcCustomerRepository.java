package customers.repositories.impl;

import customers.domain.model.Customer;
import customers.domain.state.AccountStatus;
import customers.repositories.CustomerRepository;
import customers.repositories.mappers.CustomerRowMapper;
import customers.repositories.sql.CustomerSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    // == Fields ==
    private final JdbcTemplate jdbc;
    private final RowMapper<Customer> mapper;


    // == Constructors ==
    @Autowired
    public JdbcCustomerRepository(JdbcTemplate jdbc, CustomerRowMapper mapper) {
        this.jdbc = jdbc;
        this.mapper = mapper;
    }

    // == Public - Implemented Methods ==

    @Override
    public Optional<Customer> findById(UUID id) {
        return queryForOptional(CustomerSql.FIND_BY_ID, mapper, id);
    }

    @Override
    public Optional<Customer> findByUsername(String username) {
        return queryForOptional(CustomerSql.FIND_BY_USERNAME, mapper, username);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return queryForOptional(CustomerSql.FIND_BY_EMAIL, mapper, email);
    }

    @Override
    public Optional<Customer> findByPhoneNumber(String phoneNumber) {
        return queryForOptional(CustomerSql.FIND_BY_PHONE_NUMBER, mapper, phoneNumber);
    }

    @Override
    public Customer insert(Customer customer) {
        Customer inserted = jdbc.queryForObject(CustomerSql.INSERT_CUSTOMER, mapper,
                customer.getCustomerId(),
                customer.getUsername(),
                customer.getPasswordHash(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getCustomerName(),
                customer.getCustomerSurname(),
                customer.getSubscribedAt(),
                customer.getSubscribedValidUntilAt(),
                customer.getVersion(),
                customer.getStatus().name(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getPasswordChangedAt(),
                customer.getDeletedAt());

        if (inserted == null){
            throw new IllegalStateException("Insert returned null for customer: " + customer.getCustomerId());
        }
        return inserted;
    }

    @Override
    public boolean updateProfile(Customer customer, int expectedVersion) {

        int rows = jdbc.update(CustomerSql.UPDATE_CUSTOMER,
                customer.getUsername(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getCustomerName(),
                customer.getCustomerSurname(),
                customer.getCustomerId(),
                expectedVersion);

        return rows == 1;
    }

    @Override
    public boolean updateStatus(UUID customerId, AccountStatus status, int expectedVersion) {

        int rows = jdbc.update(CustomerSql.UPDATE_STATUS,
                status.name(),
                customerId,
                expectedVersion);

        return rows == 1;
    }

    @Override
    public boolean updatePassword(UUID customerId, String newHashedPassword, int expectedVersion) {

        int rows = jdbc.update(CustomerSql.UPDATE_PASSWORD,
                newHashedPassword,
                customerId,
                expectedVersion);

        return rows == 1;
    }

    // == Private Methods ==

    private <T> Optional<T> queryForOptional(String sql, RowMapper<T> mapper, Object... args){
        try{
            T value = jdbc.queryForObject(sql, mapper, args);

            if (value == null)
                throw new IllegalStateException("RowMapper returned null for query: " + sql);

            return Optional.of(value);
        } catch (EmptyResultDataAccessException exception){
            return Optional.empty();
        }
    }
}
