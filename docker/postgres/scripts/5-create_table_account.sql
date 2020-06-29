-- Table: public.account

-- DROP TABLE public.account;

CREATE TABLE public.account
(
    account_id bigint NOT NULL,
    customer_id bigint NOT NULL,
    account_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    current_balance double precision NOT NULL,
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (account_id),
    CONSTRAINT customer_id FOREIGN KEY (customer_id)
        REFERENCES public.customer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

    TABLESPACE pg_default;

ALTER TABLE public.account
    OWNER to postgres;

GRANT ALL ON TABLE public.account TO postgres;
