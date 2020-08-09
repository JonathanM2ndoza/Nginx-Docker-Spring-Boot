package com.jmendoza.wallet.infrastructure.databases.postgresql.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmendoza.wallet.common.constants.global.DBSchema;
import com.jmendoza.wallet.common.datetime.LocalDateTimeDeserializer;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.account.Account;
import com.jmendoza.wallet.domain.ports.outbound.account.GetAccountPort;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.time.LocalDateTime;

@Component
public class GetAccountAdapter implements GetAccountPort {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LocalDateTimeDeserializer localDateTimeDeserializer;

    @Autowired
    public GetAccountAdapter(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Account getAccount(String accountId) throws GlobalException {

        final String procedureCall = "{ ? = call " + DBSchema.ACCT_SCHEMA + ".get_account(?)}";
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
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer).create();
                account = gson.fromJson(pGobject.toString(), Account.class);
            }

        } catch (Exception e) {
            throw new GlobalException("Exception getAccount: " + e.toString());
        }
        return account;
    }
}
