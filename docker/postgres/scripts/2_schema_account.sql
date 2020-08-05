-- **************************************************************************************************************
-- *  AUTHOR:       JONATHAN MENDOZA                                                                            *
-- *  NAME:         DDL_SCHEMA ACCOUNT                                                                          *
-- *  DATE:         2020-07                                                                                     *
-- *  DESCRIPTION:  SEQUENCE OF SQL SENTENCES TO GENERATE THE ACCT SCHEMA                                       *
-- *  VERSION:      v1.0                                                                                        *
-- **************************************************************************************************************

------------------------------------------------------------------------------------
-- DDL for SCHEMA ACCOUNT
------------------------------------------------------------------------------------
CREATE SCHEMA ACCT;

------------------------------------------------------------------------------------
------------------------------------------------------------------------------------
-- DDL for TABLE ACCT.ACCOUNT
------------------------------------------------------------------------------------
------------------------------------------------------------------------------------

-- DROP TABLE ACCT.ACCOUNT;

CREATE TABLE ACCT.ACCOUNT(
    ACCOUNT_ID_PK       BIGSERIAL           NOT NULL,
    CUSTOMER_ID_FK      BIGSERIAL           NOT NULL,
    ACCOUNT_NUMBER      VARCHAR(250)        NOT NULL,
    CURRENT_BALANCE     DOUBLE PRECISION    DEFAULT 0 NOT NULL,
    CREATED_AT          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT ACCOUNT_PK PRIMARY KEY (ACCOUNT_ID_PK)
);

COMMENT ON TABLE ACCT.ACCOUNT 		                IS 'Stores the account of each user.';
COMMENT ON COLUMN ACCT.ACCOUNT.ACCOUNT_ID_PK        IS 'Description of ACCOUNT_ID_PK';
COMMENT ON COLUMN ACCT.ACCOUNT.CUSTOMER_ID_FK       IS 'Description of CUSTOMER_ID_FK';
COMMENT ON COLUMN ACCT.ACCOUNT.ACCOUNT_NUMBER       IS 'Description of ACCOUNT_NUMBER';
COMMENT ON COLUMN ACCT.ACCOUNT.CURRENT_BALANCE      IS 'Description of CURRENT_BALANCE';
COMMENT ON COLUMN ACCT.ACCOUNT.CREATED_AT           IS 'Description of CREATED_AT';

-----------------------------------------------------------------------------------
-- DDL for CONTRAINST OF THE UNIQUE
-----------------------------------------------------------------------------------
ALTER TABLE ACCT.ACCOUNT   ADD CONSTRAINT ACCOUNT_NUMBER_UQ       UNIQUE (ACCOUNT_NUMBER);

------------------------------------------------------------------------------------
-- DDL for INDEXES
------------------------------------------------------------------------------------
CREATE INDEX ACCOUNT_NUMBER_INDX  ON ACCT.ACCOUNT(ACCOUNT_NUMBER);

------------------------------------------------------------------------------------
-- DDL for CONTRAINST OF THE FOREIGN KEY
------------------------------------------------------------------------------------
ALTER TABLE ACCT.ACCOUNT    ADD CONSTRAINT CUSTOMER_ID_FK    FOREIGN KEY (CUSTOMER_ID_FK)    REFERENCES AUTH.CUSTOMER (C_ID_PK);

------------------------------------------------------------------------------------
-- DDL for CREATE SEQUENCE (ACCOUNT)
------------------------------------------------------------------------------------
-- DROP SEQUENCE ACCT.ACCOUNT_ID_PK_SEQ;

CREATE SEQUENCE ACCT.ACCOUNT_ID_PK_SEQ
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

------------------------------------------------------------------------------------
-- DDL for FUNCTION ACCT.CREATE_ACCOUNT
------------------------------------------------------------------------------------

-- DROP FUNCTION ACCT.CREATE_ACCOUNT(BIGINT, VARCHAR, DOUBLE PRECISION, TIMESTAMP WITHOUT TIME ZONE);

CREATE OR REPLACE FUNCTION ACCT.CREATE_ACCOUNT(
	p_customer_id       BIGINT,
	p_account_number    VARCHAR,
	p_current_balance   DOUBLE PRECISION,
	p_created_at        TIMESTAMP WITHOUT TIME ZONE)
    RETURNS BIGINT
    LANGUAGE 'plpgsql'

AS $BODY$
DECLARE
    p_account_id BIGINT;
BEGIN
    p_account_id := nextval('ACCT.ACCOUNT_ID_PK_SEQ');
    INSERT INTO ACCT.ACCOUNT(
	ACCOUNT_ID_PK, CUSTOMER_ID_FK, ACCOUNT_NUMBER, CURRENT_BALANCE, CREATED_AT)
	VALUES (p_account_id, p_customer_id, p_account_number, p_current_balance, p_created_at);

    RETURN p_account_id;

END;
$BODY$;

------------------------------------------------------------------------------------
-- DDL for FUNCTION ACCT.GET_ACCOUNT
------------------------------------------------------------------------------------
-- DROP FUNCTION ACCT.GET_ACCOUNT(BIGINT);

CREATE OR REPLACE FUNCTION ACCT.GET_ACCOUNT(
	p_account_id    BIGINT)
    RETURNS json
    LANGUAGE 'plpgsql'

AS $BODY$
DECLARE
    json_result json;
BEGIN

    json_result:= (SELECT
                       json_build_object(
                               'accountId', ac.ACCOUNT_ID_PK,
                               'customerId', ac.CUSTOMER_ID_FK,
                               'accountNumber', ac.ACCOUNT_NUMBER,
                               'currentBalance', ac.CURRENT_BALANCE,
					   		   'createdAt', ac.CREATED_AT)
                   FROM ACCT.ACCOUNT AS ac
                   WHERE ac.ACCOUNT_ID_PK = p_account_id);

    RETURN json_result;

END;
$BODY$;
