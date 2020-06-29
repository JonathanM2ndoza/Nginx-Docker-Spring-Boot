-- Table: public.transaction

-- DROP TABLE public.transaction;

CREATE TABLE public.transaction
(
    transaction_id bigint NOT NULL,
    account_id bigint NOT NULL,
    transaction_type_id bigint NOT NULL,
    amount double precision NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id),
    CONSTRAINT account_id FOREIGN KEY (account_id)
        REFERENCES public.account (account_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT transaction_type_id FOREIGN KEY (transaction_type_id)
        REFERENCES public.transaction_type (transaction_type_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.transaction
    OWNER to postgres;

GRANT ALL ON TABLE public.transaction TO postgres;
