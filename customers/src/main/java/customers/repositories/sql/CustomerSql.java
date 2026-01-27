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
}
