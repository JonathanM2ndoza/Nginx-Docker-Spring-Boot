-- Table: public.role

-- DROP TABLE public.role;

CREATE TABLE public.role
(
    id integer NOT NULL,
    name character varying(20) COLLATE pg_catalog."default",
    CONSTRAINT role_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.role
    OWNER to postgres;
