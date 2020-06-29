-- FUNCTION: public.get_transaction(bigint)

-- DROP FUNCTION public.get_transaction(bigint);

CREATE OR REPLACE FUNCTION public.get_transaction(
	p_transaction_id bigint)
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
					   'transactionId',tx.transaction_id,
                       'accountId', tx.account_id,
                       'transactionTypeId',tx.transaction_type_id,
                       'amount',tx.amount,
				       'description',tx.description,
					   'createdAt',tx.created_at)
				   FROM public.transaction AS tx
               	   WHERE tx.transaction_id = p_transaction_id);
    RETURN json_result;

END;
$BODY$;

ALTER FUNCTION public.get_transaction(bigint)
    OWNER TO postgres;

GRANT EXECUTE ON FUNCTION public.get_transaction(bigint) TO postgres;

GRANT EXECUTE ON FUNCTION public.get_transaction(bigint) TO PUBLIC;

