-- **************************************************************************************************************
-- *  AUTHOR:       JONATHAN MENDOZA                                                                            *
-- *  NAME:         DDL_SCHEMA TRANS                                                                            *
-- *  DATE:         2020-07                                                                                     *
-- *  DESCRIPTION:  SEQUENCE OF SQL SENTENCES TO GENERATE THE TRANS SCHEMA                                      *
-- *  VERSION:      v1.0                                                                                        *
-- **************************************************************************************************************

------------------------------------------------------------------------------------
-- DDL for SCHEMA TRANSACTION
------------------------------------------------------------------------------------
CREATE SCHEMA TRANS;

------------------------------------------------------------------------------------
------------------------------------------------------------------------------------
-- DDL for TABLE TRANS.TRANSACTION_TYPE
------------------------------------------------------------------------------------
------------------------------------------------------------------------------------
-- DROP TABLE TRANS.TRANSACTION_TYPE;

CREATE TABLE TRANS.TRANSACTION_TYPE(
    TRANSACTION_TYPE_ID_PK      BIGSERIAL       NOT NULL,
    DESCRIPTION                 VARCHAR(250)    NOT NULL,
    CONSTRAINT TRANSACTION_TYPE_PK PRIMARY KEY (TRANSACTION_TYPE_ID_PK)
);

COMMENT ON TABLE  TRANS.TRANSACTION_TYPE 		                IS 'Stores the transaction_type.';
COMMENT ON COLUMN TRANS.TRANSACTION_TYPE.TRANSACTION_TYPE_ID_PK IS 'Description of TRANSACTION_TYPE_ID_PK';
COMMENT ON COLUMN TRANS.TRANSACTION_TYPE.DESCRIPTION            IS 'Description of DESCRIPTION';

-----------------------------------------------------------------------------------
-- DDL for CONTRAINST OF THE UNIQUE
-----------------------------------------------------------------------------------
ALTER TABLE TRANS.TRANSACTION_TYPE   ADD CONSTRAINT DESCRIPTION_UQ    UNIQUE (DESCRIPTION);

------------------------------------------------------------------------------------
-- DML for TABLE TRANS.TRANSACTION_TYPE
------------------------------------------------------------------------------------
INSERT INTO TRANS.TRANSACTION_TYPE (DESCRIPTION) VALUES ('Deposit');
INSERT INTO TRANS.TRANSACTION_TYPE (DESCRIPTION) VALUES ('Withdrawal');

------------------------------------------------------------------------------------
------------------------------------------------------------------------------------
-- DDL for TABLE TRANS.TRANSACTION
------------------------------------------------------------------------------------
------------------------------------------------------------------------------------
-- DROP TABLE TRANS.TRANSACTION;
CREATE TABLE TRANS.TRANSACTION(
    TRANSACTION_ID_PK           BIGSERIAL           NOT NULL,
    ACCOUNT_ID_FK               BIGSERIAL           NOT NULL,
    TRANSACTION_TYPE_ID_FK      BIGSERIAL           NOT NULL,
    AMOUNT                      DOUBLE PRECISION    NOT NULL,
    DESCRIPTION                 VARCHAR(250)        NOT NULL,
    CREATED_AT                  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT TRANSACTION_PK PRIMARY KEY (TRANSACTION_ID_PK)
);

------------------------------------------------------------------------------------
-- DDL for CONTRAINST OF THE FOREIGN KEY
------------------------------------------------------------------------------------
ALTER TABLE TRANS.TRANSACTION    ADD CONSTRAINT ACCOUNT_ID_FK           FOREIGN KEY (ACCOUNT_ID_FK)             REFERENCES ACCT.ACCOUNT (ACCOUNT_ID_PK);
ALTER TABLE TRANS.TRANSACTION    ADD CONSTRAINT TRANSACTION_TYPE_ID_FK  FOREIGN KEY (TRANSACTION_TYPE_ID_FK)    REFERENCES TRANS.TRANSACTION_TYPE (TRANSACTION_TYPE_ID_PK);

------------------------------------------------------------------------------------
-- DDL for CREATE SEQUENCE (TRANSACTION)
------------------------------------------------------------------------------------
-- DROP SEQUENCE TRANS.TRANSACTION_ID_PK_SEQ;

CREATE SEQUENCE TRANS.TRANSACTION_ID_PK_SEQ
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

------------------------------------------------------------------------------------
-- DDL for FUNCTION TRANS.CREATE_TRANSACTION
------------------------------------------------------------------------------------
-- DROP FUNCTION TRANS.CREATE_TRANSACTION(BIGINT, BIGINT, DOUBLE PRECISION, VARCHAR, TIMESTAMP WITHOUT TIME ZONE);

CREATE OR REPLACE FUNCTION TRANS.CREATE_TRANSACTION(
	p_account_id                BIGINT,
	p_transaction_type_id       BIGINT,
	p_amount                    DOUBLE PRECISION,
	p_description               VARCHAR,
	p_created_at                TIMESTAMP WITHOUT TIME ZONE)
    RETURNS bigint
    LANGUAGE 'plpgsql'

AS $BODY$
DECLARE
    p_transaction_id BIGINT;
	p_current_balance DOUBLE PRECISION;
BEGIN
	SELECT CURRENT_BALANCE INTO p_current_balance FROM ACCT.ACCOUNT WHERE ACCOUNT_ID_PK = p_account_id;

	IF p_current_balance <= 0 AND p_transaction_type_id = 2 THEN
		RAISE EXCEPTION 'Current balance less than zero';
	END IF;

    p_transaction_id := nextval('TRANS.TRANSACTION_ID_PK_SEQ');
    INSERT INTO TRANS.TRANSACTION(
	TRANSACTION_ID_PK, ACCOUNT_ID_FK, TRANSACTION_TYPE_ID_FK, AMOUNT, DESCRIPTION, CREATED_AT)
	VALUES (p_transaction_id, p_account_id, p_transaction_type_id, p_amount, p_description, p_created_at);

	IF p_transaction_type_id = 1 THEN
	   p_current_balance:= p_current_balance + p_amount;
	ELSE
	   p_current_balance:= p_current_balance - p_amount;
	END IF;

	UPDATE ACCT.ACCOUNT
	SET CURRENT_BALANCE=p_current_balance
	WHERE ACCOUNT_ID_PK = p_account_id;

    RETURN p_transaction_id;

END;
$BODY$;

------------------------------------------------------------------------------------
-- DDL for FUNCTION TRANS.GET_TRANSACTIONS_ACCOUNT
------------------------------------------------------------------------------------
-- DROP FUNCTION TRANS.GET_TRANSACTIONS_ACCOUNT(BIGINT);

CREATE OR REPLACE FUNCTION TRANS.GET_TRANSACTIONS_ACCOUNT(
	p_account_id    BIGINT)
    RETURNS json
    LANGUAGE 'plpgsql'

AS $BODY$
DECLARE
    json_transactions json;
	json_result json;
BEGIN

	json_transactions:= (SELECT
						 json_agg(
							 json_build_object(
								 'transactionId',tx.TRANSACTION_ID_PK,
                                 'accountId', tx.ACCOUNT_ID_FK,
                                 'transactionTypeId',tx.TRANSACTION_TYPE_ID_FK,
                                 'amount',tx.AMOUNT,
							     'description',tx.DESCRIPTION,
							     'createdAt',tx.CREATED_AT))
						 FROM TRANS.TRANSACTION AS tx
               			 WHERE tx.ACCOUNT_ID_FK = p_account_id);

    json_result:= (SELECT
                       json_build_object(
                               'accountId', ac.ACCOUNT_ID_PK,
                               'customerId', ac.CUSTOMER_ID_FK,
                               'accountNumber', ac.ACCOUNT_NUMBER,
                               'currentBalance', ac.CURRENT_BALANCE,
					   		   'createdAt', ac.CREATED_AT,
					           'transactionList', json_transactions)
                   FROM ACCT.ACCOUNT AS ac
                   WHERE ac.ACCOUNT_ID_PK = p_account_id);

    RETURN json_result;

END;
$BODY$;

------------------------------------------------------------------------------------
-- DDL for FUNCTION TRANS.GET_TRANSACTION
------------------------------------------------------------------------------------
-- DROP FUNCTION TRANS.GET_TRANSACTION(BIGINT);

CREATE OR REPLACE FUNCTION TRANS.GET_TRANSACTION(
	p_transaction_id    BIGINT)
    RETURNS json
    LANGUAGE 'plpgsql'

AS $BODY$
DECLARE
	json_result json;
BEGIN

	json_result:= (SELECT
				   json_build_object(
					   'transactionId',tx.TRANSACTION_ID_PK,
                       'accountId', tx.ACCOUNT_ID_FK,
                       'transactionTypeId',tx.TRANSACTION_TYPE_ID_FK,
                       'amount',tx.AMOUNT,
				       'description',tx.DESCRIPTION,
					   'createdAt',tx.CREATED_AT)
				   FROM TRANS.TRANSACTION AS tx
               	   WHERE tx.TRANSACTION_ID_PK = p_transaction_id);
    RETURN json_result;

END;
$BODY$;
