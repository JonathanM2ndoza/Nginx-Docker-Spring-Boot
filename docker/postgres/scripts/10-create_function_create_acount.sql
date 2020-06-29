-- FUNCTION: public.create_acount(bigint, character varying, double precision, timestamp with time zone)

-- DROP FUNCTION public.create_acount(bigint, character varying, double precision, timestamp with time zone);

CREATE OR REPLACE FUNCTION public.create_acount(
	p_customer_id bigint,
	p_account_number character varying,
	p_current_balance double precision,
	p_created_at timestamp with time zone)
    RETURNS bigint
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE

AS $BODY$
DECLARE
    p_account_id bigint;
BEGIN
    p_account_id := nextval('account_id_seq');
    INSERT INTO public.account(
	account_id, customer_id, account_number, current_balance, created_at)
	VALUES (p_account_id, p_customer_id, p_account_number, p_current_balance, p_created_at);

    RETURN p_account_id;

END;
$BODY$;

ALTER FUNCTION public.create_acount(bigint, character varying, double precision, timestamp with time zone)
    OWNER TO postgres;

GRANT EXECUTE ON FUNCTION public.create_acount(bigint, character varying, double precision, timestamp with time zone) TO postgres;

GRANT EXECUTE ON FUNCTION public.create_acount(bigint, character varying, double precision, timestamp with time zone) TO PUBLIC;

