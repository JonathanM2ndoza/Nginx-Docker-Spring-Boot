package com.jmendoza.wallet.infrastructure.databases.postgresql.account;

import com.jmendoza.wallet.common.constants.global.DBSchema;
import com.jmendoza.wallet.common.datetime.UtilDateTime;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.account.Account;
import com.jmendoza.wallet.domain.ports.outbound.account.CreateAccountPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

@Component
public class CreateAccountAdapter implements CreateAccountPort {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UtilDateTime utilDateTime;

    @Autowired
    public CreateAccountAdapter(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createAccount(Account account) throws GlobalException {

        final String functionCall = "{ ? = call " + DBSchema.ACCT_SCHEMA + ".create_account(?, ?, ?, ?)}";

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             CallableStatement callableStatement = connection.prepareCall(functionCall)
        ) {
            callableStatement.registerOutParameter(1, Types.BIGINT);
            callableStatement.setLong(2, Long.parseLong(account.getCustomerId()));
            callableStatement.setString(3, account.getAccountNumber());
            callableStatement.setDouble(4, account.getCurrentBalance());
            callableStatement.setTimestamp(5, utilDateTime.localDateTimeToTimestamp(account.getCreatedAt()), utilDateTime.getTimeZone());

            callableStatement.execute();

            account.setAccountId(Long.toString(callableStatement.getLong(1)));
        } catch (Exception e) {
            throw new GlobalException("Exception createAccount: " + e.toString());
        }
    }
}
