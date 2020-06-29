-- Table: public.customer

-- DROP TABLE public.customer;

CREATE TABLE public.customer
(
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    CONSTRAINT customer_pkey PRIMARY KEY (id),
    CONSTRAINT uk_dwk6cx0afu8bs9o4t536v1j5v UNIQUE (email)
)

    TABLESPACE pg_default;

ALTER TABLE public.customer
    OWNER to postgres;
