-- FUNCTION: public.get_transactions_account(bigint)

-- DROP FUNCTION public.get_transactions_account(bigint);

CREATE OR REPLACE FUNCTION public.get_transactions_account(
	p_account_id bigint)
    RETURNS json
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE

AS $BODY$
DECLARE
    json_transactions json;
	json_result json;
BEGIN

	json_transactions:= (SELECT
						 json_agg(
							 json_build_object(
								 'transactionId',tx.transaction_id,
                                 'accountId', tx.account_id,
                                 'transactionTypeId',tx.transaction_type_id,
                                 'amount',tx.amount,
							     'description',tx.description,
							     'createdAt',tx.created_at))
						 FROM public.transaction AS tx
               			 WHERE tx.account_id = p_account_id);

    json_result:= (SELECT
                       json_build_object(
                               'accountId', ac.account_id,
                               'customerId', ac.customer_id,
                               'accountNumber', ac.account_number,
                               'currentBalance', ac.current_balance,
					   		   'createdAt', ac.created_at,
					           'transactionList', json_transactions)
                   FROM public.account AS ac
                   WHERE ac.account_id = p_account_id);

    RETURN json_result;

END;
$BODY$;

ALTER FUNCTION public.get_transactions_account(bigint)
    OWNER TO postgres;

GRANT EXECUTE ON FUNCTION public.get_transactions_account(bigint) TO postgres;

GRANT EXECUTE ON FUNCTION public.get_transactions_account(bigint) TO PUBLIC;

