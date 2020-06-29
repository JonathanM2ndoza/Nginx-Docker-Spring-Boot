-- Table: public.customer_role

-- DROP TABLE public.customer_role;

CREATE TABLE public.customer_role
(
    customer_id bigint NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT customer_role_pkey PRIMARY KEY (customer_id, role_id),
    CONSTRAINT fkipr3etk2mjwkv6ahb4x4vwqy3 FOREIGN KEY (customer_id)
        REFERENCES public.customer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkmwml8muyov9xhw4423lp88n2u FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.customer_role
    OWNER to postgres;
