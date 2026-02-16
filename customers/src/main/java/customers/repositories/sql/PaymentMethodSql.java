package customers.repositories.sql;

public final class PaymentMethodSql {

    private PaymentMethodSql(){}

    // == Public Static Queries for JdbcPaymentMethodRepository

    public static final String INSERT_NEW_PAYMENT_METHOD = """
            INSERT INTO customer_payment_methods(
            customer_payment_id,
            customer_id,
            provider,
            brand,
            payment_ref_token,
            exp_year,
            exp_month
            )
            VALUES
            (?,?,?,?,?,?,?)
            RETURNING *
            """;

    public static final String GET_ALL_PAGED_PAYMENT_METHODS_FOR_CUSTOMER = """
            SELECT * FROM customer_payment_methods
            WHERE customer_id = ?
            ORDER BY created_at DESC LIMIT ? OFFSET ?;
            """;

    public static final String REPLACE_EXISTING_PAYMENT_METHOD = """
            UPDATE customer_payment_methods
            SET
            provider = ?,
            brand = ?,
            payment_ref_token = ?,
            exp_year = ?,
            exp_month = ?,
            status = 'PENDING',
            version = version + 1,
            updated_at = now()
            WHERE customer_id = ?
            AND customer_payment_id = ?
            AND version = ?;
            """;

    public static final String UPDATE_REF_TOKEN = """
            UPDATE customer_payment_methods
            SET
            payment_ref_token = ?,
            status = 'PENDING',
            version = version + 1,
            updated_at = now()
            WHERE customer_id = ?
            AND customer_payment_id = ?
            AND version = ?;
            """;

    public static final String FIND_PAYMENT_METHOD = """
            SELECT * FROM customer_payment_methods
            WHERE customer_id = ?
            AND customer_payment_id = ?
            """;

    public static final String UPDATE_METHOD_STATUS = """
            UPDATE customer_payment_methods
            SET
            status = ?::token_status,
            version = version + 1,
            updated_at = now()
            WHERE customer_id = ?
            AND customer_payment_id = ?
            AND version = ?;
            """;

    public static final String DELETE_PAYMENT_METHOD = """
            DELETE FROM customer_payment_methods
            WHERE customer_id = ?
            AND customer_payment_id = ?
            AND version = ?
            """;

    public static final String REMOVE_DEFAULT_FROM_CUSTOMER = """
            UPDATE customer_payment_methods
            SET
            is_default = false,
            updated_at = now()
            WHERE customer_id = ?
            AND is_default = true
            """;

    public static final String SET_NEW_DEFAULT_FROM_CUSTOMER = """
            UPDATE customer_payment_methods
            SET
            is_default = true,
            updated_at = now()
            WHERE customer_id = ?
            AND customer_payment_id = ?
            """;

    public static final String GET_COUNT_OF_ALL_CUSTOMER_PAYMENT_METHODS = """
            SELECT COUNT(*) FROM customer_payment_methods
            WHERE customer_id = ?;
            """;
}