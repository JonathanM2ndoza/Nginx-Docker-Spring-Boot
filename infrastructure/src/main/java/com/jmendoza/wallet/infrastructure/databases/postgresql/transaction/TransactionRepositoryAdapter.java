package com.jmendoza.wallet.infrastructure.databases.postgresql.transaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmendoza.wallet.common.datetime.OffsetDateTimeDeserializer;
import com.jmendoza.wallet.common.datetime.UtilDateTime;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.account.Account;
import com.jmendoza.wallet.domain.model.transaction.Transaction;
import com.jmendoza.wallet.domain.ports.outbound.transaction.CreateTransactionPort;
import com.jmendoza.wallet.domain.ports.outbound.transaction.GetTransactionPort;
import com.jmendoza.wallet.domain.ports.outbound.transaction.GetTransactionsByAccountIdPort;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.time.OffsetDateTime;

@Component
public class TransactionRepositoryAdapter implements CreateTransactionPort, GetTransactionsByAccountIdPort, GetTransactionPort {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UtilDateTime utilDateTime;
    @Autowired
    private OffsetDateTimeDeserializer offsetDateTimeDeserializer;

    @Override
    public void createTransaction(Transaction transaction) throws GlobalException {

        final String functionCall = "{ ? = call create_transaction(?, ?, ?, ?, ?)}";

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             CallableStatement callableStatement = connection.prepareCall(functionCall)
        ) {
            callableStatement.registerOutParameter(1, Types.BIGINT);
            callableStatement.setLong(2, Long.parseLong(transaction.getAccountId()));
            callableStatement.setLong(3, Long.parseLong(transaction.getTransactionTypeId()));
            callableStatement.setDouble(4, transaction.getAmount());
            callableStatement.setString(5, transaction.getDescription());
            callableStatement.setObject(6, utilDateTime.getDateTimeByZoneId());

            callableStatement.execute();

            transaction.setTransactionId(Long.toString(callableStatement.getLong(1)));
        } catch (Exception e) {
            throw new GlobalException("Exception createTransaction: " + e.toString());
        }
    }

    @Override
    public Account getTransactionsByAccountId(String accountId) throws GlobalException {

        final String procedureCall = "{ ? = call get_transactions_account(?)}";
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
            throw new GlobalException("Exception getTransactionsByAccountId: " + e.toString());
        }
        return account;
    }

    @Override
    public Transaction getTransaction(String transactionId) throws GlobalException {

        final String procedureCall = "{ ? = call get_transaction(?)}";
        Transaction transaction = null;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             CallableStatement callableStatement = connection.prepareCall(procedureCall)
        ) {
            PGobject pGobject;

            callableStatement.registerOutParameter(1, Types.OTHER);
            callableStatement.setLong(2, Long.parseLong(transactionId));
            callableStatement.execute();

            pGobject = (PGobject) callableStatement.getObject(1);
            if (pGobject != null) {
                Gson gson = new GsonBuilder().registerTypeAdapter(OffsetDateTime.class, offsetDateTimeDeserializer).create();
                transaction = gson.fromJson(pGobject.toString(), Transaction.class);
            }

        } catch (Exception e) {
            throw new GlobalException("Exception getTransaction: " + e.toString());
        }
        return transaction;
    }
}
