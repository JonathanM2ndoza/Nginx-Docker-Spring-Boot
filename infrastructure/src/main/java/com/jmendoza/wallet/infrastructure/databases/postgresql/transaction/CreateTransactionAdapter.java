package com.jmendoza.wallet.infrastructure.databases.postgresql.transaction;

import com.jmendoza.wallet.common.constants.global.DBSchema;
import com.jmendoza.wallet.common.datetime.UtilDateTime;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.transaction.Transaction;
import com.jmendoza.wallet.domain.ports.outbound.transaction.CreateTransactionPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

@Component
public class CreateTransactionAdapter implements CreateTransactionPort {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UtilDateTime utilDateTime;

    @Override
    public void createTransaction(Transaction transaction) throws GlobalException {

        final String functionCall = "{ ? = call " + DBSchema.TRANS_SCHEMA + ".create_transaction(?, ?, ?, ?, ?)}";

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             CallableStatement callableStatement = connection.prepareCall(functionCall)
        ) {
            callableStatement.registerOutParameter(1, Types.BIGINT);
            callableStatement.setLong(2, Long.parseLong(transaction.getAccountId()));
            callableStatement.setLong(3, Long.parseLong(transaction.getTransactionTypeId()));
            callableStatement.setDouble(4, transaction.getAmount());
            callableStatement.setString(5, transaction.getDescription());
            callableStatement.setTimestamp(6, utilDateTime.getCurrentTimestamp(), utilDateTime.getTimeZone());

            callableStatement.execute();

            transaction.setTransactionId(Long.toString(callableStatement.getLong(1)));
        } catch (Exception e) {
            throw new GlobalException("Exception createTransaction: " + e.toString());
        }
    }
}
