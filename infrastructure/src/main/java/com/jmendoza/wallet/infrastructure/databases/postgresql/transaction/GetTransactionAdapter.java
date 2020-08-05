package com.jmendoza.wallet.infrastructure.databases.postgresql.transaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmendoza.wallet.common.constants.global.DBSchema;
import com.jmendoza.wallet.common.datetime.LocalDateTimeDeserializer;
import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.transaction.Transaction;
import com.jmendoza.wallet.domain.ports.outbound.transaction.GetTransactionPort;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.time.LocalDateTime;

@Component
public class GetTransactionAdapter implements GetTransactionPort {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LocalDateTimeDeserializer localDateTimeDeserializer;

    @Override
    public Transaction getTransaction(String transactionId) throws GlobalException {

        final String procedureCall = "{ ? = call " + DBSchema.TRANS_SCHEMA + ".get_transaction(?)}";
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
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer).create();
                transaction = gson.fromJson(pGobject.toString(), Transaction.class);
            }

        } catch (Exception e) {
            throw new GlobalException("Exception getTransaction: " + e.toString());
        }
        return transaction;
    }
}
