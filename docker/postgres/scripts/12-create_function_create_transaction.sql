-- FUNCTION: public.create_transaction(bigint, bigint, double precision, character varying, timestamp with time zone)

-- DROP FUNCTION public.create_transaction(bigint, bigint, double precision, character varying, timestamp with time zone);

CREATE OR REPLACE FUNCTION public.create_transaction(
	p_account_id bigint,
	p_transaction_type_id bigint,
	p_amount double precision,
	p_description character varying,
	p_created_at timestamp with time zone)
    RETURNS bigint
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE

AS $BODY$
DECLARE
    p_transaction_id bigint;
	p_current_balance double precision;
BEGIN
	SELECT current_balance INTO p_current_balance FROM public.account WHERE account_id = p_account_id;

	IF p_current_balance <= 0 AND p_transaction_type_id = 2 THEN
		RAISE EXCEPTION 'Current balance less than zero';
	END IF;

    p_transaction_id := nextval('transaction_id_seq');
    INSERT INTO public.transaction(
	transaction_id, account_id, transaction_type_id, amount, description, created_at)
	VALUES (p_transaction_id, p_account_id, p_transaction_type_id, p_amount, p_description, p_created_at);

	IF p_transaction_type_id = 1 THEN
	   p_current_balance:= p_current_balance + p_amount;
	ELSE
	   p_current_balance:= p_current_balance - p_amount;
	END IF;

	UPDATE public.account
	SET current_balance=p_current_balance
	WHERE account_id = p_account_id;

    RETURN p_transaction_id;

END;
$BODY$;

ALTER FUNCTION public.create_transaction(bigint, bigint, double precision, character varying, timestamp with time zone)
    OWNER TO postgres;

GRANT EXECUTE ON FUNCTION public.create_transaction(bigint, bigint, double precision, character varying, timestamp with time zone) TO postgres;

GRANT EXECUTE ON FUNCTION public.create_transaction(bigint, bigint, double precision, character varying, timestamp with time zone) TO PUBLIC;

