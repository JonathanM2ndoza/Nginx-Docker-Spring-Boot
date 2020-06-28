package com.jmendoza.wallet.infrastructure.databases.postgresql.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmendoza.wallet.common.datetime.OffsetDateTimeDeserializer;
import com.jmendoza.wallet.common.datetime.UtilDateTime;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.account.Account;
import com.jmendoza.wallet.domain.ports.outbound.account.CreateAccountPort;
import com.jmendoza.wallet.domain.ports.outbound.account.GetAccountPort;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.time.OffsetDateTime;

@Component
public class AccountRepositoryAdapter implements CreateAccountPort, GetAccountPort {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UtilDateTime utilDateTime;
    @Autowired
    private OffsetDateTimeDeserializer offsetDateTimeDeserializer;

    @Override
    public void createAccount(Account account) throws GlobalException {

        final String functionCall = "{ ? = call create_acount(?, ?, ?, ?)}";

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             CallableStatement callableStatement = connection.prepareCall(functionCall)
        ) {
            callableStatement.registerOutParameter(1, Types.BIGINT);
            callableStatement.setLong(2, Long.parseLong(account.getCustomerId()));
            callableStatement.setString(3, account.getAccountNumber());
            callableStatement.setDouble(4, account.getCurrentBalance());
            callableStatement.setObject(5, utilDateTime.getDateTimeByZoneId());

            callableStatement.execute();

            account.setAccountId(Long.toString(callableStatement.getLong(1)));
        } catch (Exception e) {
            throw new GlobalException("Exception createAccount: " + e.toString());
        }
    }

    @Override
    public Account getAccount(String accountId) throws GlobalException {

        final String procedureCall = "{ ? = call get_account(?)}";
        Account account = null;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             CallableStatement callableStatement = connection.prepareCall(procedureCall)
        ) {
            PGobject pGobject;

            callableStatement.registerOutParameter(1, Types.OTHER);
            callableStatement.setLong(2, Long.parseLong(accountId));
            callableStatement.execute();

            pGobject = (PGobject) callableStatement.getObject(1);
            if (pGobject != null) {
                Gson gson = new GsonBuilder().registerTypeAdapter(OffsetDateTime.class, offsetDateTimeDeserializer).create();
                account = gson.fromJson(pGobject.toString(), Account.class);
            }

        } catch (Exception e) {
            throw new GlobalException("Exception getAccount: " + e.toString());
        }
        return account;
    }
}
