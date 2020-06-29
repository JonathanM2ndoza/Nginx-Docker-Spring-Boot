-- Table: public.transaction_type

-- DROP TABLE public.transaction_type;

CREATE TABLE public.transaction_type
(
    transaction_type_id bigint NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT transaction_type_pkey PRIMARY KEY (transaction_type_id)
)

    TABLESPACE pg_default;

ALTER TABLE public.transaction_type
    OWNER to postgres;

GRANT ALL ON TABLE public.transaction_type TO postgres;
