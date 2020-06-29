-- FUNCTION: public.get_account(bigint)

-- DROP FUNCTION public.get_account(bigint);

CREATE OR REPLACE FUNCTION public.get_account(
	p_account_id bigint)
    RETURNS json
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE

AS $BODY$
DECLARE
    json_result json;
BEGIN

    json_result:= (SELECT
                       json_build_object(
                               'accountId', ac.account_id,
                               'customerId', ac.customer_id,
                               'accountNumber', ac.account_number,
                               'currentBalance', ac.current_balance,
					   		   'createdAt', ac.created_at)
                   FROM public.account AS ac
                   WHERE ac.account_id = p_account_id);

    RETURN json_result;

END;
$BODY$;

ALTER FUNCTION public.get_account(bigint)
    OWNER TO postgres;

GRANT EXECUTE ON FUNCTION public.get_account(bigint) TO postgres;

GRANT EXECUTE ON FUNCTION public.get_account(bigint) TO PUBLIC;

