package customers.repositories.sql;

public final class CustomerSql {

    private CustomerSql(){}

    // == Public Static Queries for JdbcCustomerRepo

    public static final String FIND_BY_ID = """
            SELECT * FROM customers
            WHERE
            customer_id = ?
            """;

    public static final String FIND_BY_USERNAME = """
            SELECT * FROM customers
            WHERE
            username = ?
            """;

    public static final String FIND_BY_EMAIL = """
            SELECT * FROM customers
            WHERE
            email = ?
            """;

    public static final String FIND_BY_PHONE_NUMBER = """
            SELECT * FROM customers
            WHERE
            phone_number = ?
            """;

    public static final String INSERT_CUSTOMER = """
            INSERT INTO customers (
                customer_id,
                username,
                password_hash,
                phone_number,
                email,
                customer_name,
                customer_surname,
                subscribed_at,
                subscribed_valid_until_at,
                version,
                status,
                created_at,
                updated_at,
                password_changed_at,
                deleted_at
            )
            VALUES (
                ?, ?, ?, ?, ?, ?, ?, null, null, 0, ?::account_status, now(), now(), null, null
            )
            RETURNING *
            """;

    public static final String UPDATE_CUSTOMER = """
            UPDATE customers
            SET
                username = ?,
                phone_number = ?,
                email = ?,
                customer_name = ?,
                customer_surname = ?,
                updated_at = now(),
                version = version + 1
            WHERE customer_id = ?
              AND version = ?;
            """;

    public static final String UPDATE_STATUS = """
            UPDATE customers
            SET
                status = ?::account_status,
                updated_at = now(),
                version = version + 1
            WHERE customer_id = ?
            AND version = ?
            """;

    public static final String UPDATE_PASSWORD = """
            UPDATE customers
            SET
                password_hash = ?,
                password_changed_at = now(),
                updated_at = now(),
                version = version + 1
            WHERE customer_id = ?
            AND version = ?
            """;
}
