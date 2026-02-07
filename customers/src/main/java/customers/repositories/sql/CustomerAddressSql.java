package customers.repositories.sql;

public class CustomerAddressSql {

    public static final String INSERT_CUSTOMER_ADDRESS = """
            INSERT INTO customer_addresses (
            customer_id,
            country,
            city,
            street,
            postal_code,
            is_default,
            version,
            created_at,
            updated_at
            )
            VALUES
            (?,?,?,?,?,false,0,now(),now())
            RETURNING *
            """;

    public static final String UPDATE_CUSTOMER_ADDRESS = """
            UPDATE customer_addresses
            SET
            country = ?,
            city = ?,
            street = ?,
            postal_code = ?,
            version = version + 1,
            updated_at = now()
            WHERE address_id = ?
            AND customer_id = ?
            AND version = ?
            """;

    public static final String DELETE_CUSTOMER_ADDRESS = """
            DELETE FROM customer_addresses
            WHERE address_id = ?
            AND customer_id = ?
            And version = ?;
            """;

    public static final String SET_CUSTOMER_ADDRESS_TO_DEFAULT = """
            UPDATE customer_addresses ca
            SET
              is_default = (ca.address_id = ?),
              version = CASE
                  WHEN ca.is_default IS DISTINCT FROM (ca.address_id = ?) THEN ca.version + 1
                  ELSE ca.version
              END,
              updated_at = CASE
                  WHEN ca.is_default IS DISTINCT FROM (ca.address_id = ?) THEN now()
                  ELSE ca.updated_at
              END
            WHERE ca.customer_id = ?
              AND EXISTS (
                  SELECT 1
                  FROM customer_addresses x
                  WHERE x.customer_id = ca.customer_id
                    AND x.address_id = ?
              );
            """;

    public static final String SET_DEFAULT_ADDRESS_TO_FALSE = """
            UPDATE customer_addresses
            SET
              is_default = false,
              version = version + 1,
              updated_at = now()
            WHERE customer_id = ?
            AND is_default = true
            """;

    public static final String SET_ADDRESS_TO_DEFAULT = """
            UPDATE customer_addresses
            SET
              is_default = true,
              version = version + 1,
              updated_at = now()
            WHERE customer_id = ?
            AND address_id = ?;
            """;

    public static final String FIND_CUSTOMER_ADDRESS_BY_ID = """
            SELECT * FROM customer_addresses
            WHERE address_id = ?
            AND customer_id = ?;
            """;

    public static final String GET_ALL_CUSTOMER_ADDRESSES = """
            SELECT * FROM customer_addresses
            WHERE customer_id = ?
            ORDER BY created_at DESC LIMIT ? OFFSET ?;
            """;

    public static final String GET_COUNT_OF_ALL_CUSTOMER_ADDRESSES = """
            SELECT COUNT(*) FROM customer_addresses
            WHERE customer_id = ?;
            """;
}
