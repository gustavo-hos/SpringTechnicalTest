-- Create Tables
CREATE TABLE IF NOT EXISTS public.om_item (
        id bigint NOT NULL,
        name character varying(100) NOT NULL,
        sku character varying(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS public.om_item_stock (
        id bigint NOT NULL,
        quantity integer,
        fk_item bigint
);

CREATE TABLE IF NOT EXISTS public.om_order (
        uuid character varying(36) NOT NULL,
        quantity integer,
        supplied integer,
        status character varying(40),
        creation_date timestamp without time zone,
        fk_item bigint NOT NULL,
        fk_user bigint NOT NULL
    );

CREATE TABLE IF NOT EXISTS public.om_stock_movement (
        id bigint NOT NULL,
        creation_date timestamp without time zone,
        quantity integer,
        fk_item bigint
);

CREATE TABLE IF NOT EXISTS public.om_user (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS public.om_order_stock_movement (
    fk_order character varying(36),
    fk_stock_movement bigint
);

-- Create Sequences
CREATE SEQUENCE public.om_item_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.om_item_stock_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.om_stock_movement_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.om_user_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Define Primary Keys
ALTER TABLE public.om_item ADD CONSTRAINT om_item_pkey PRIMARY KEY (id);
ALTER TABLE public.om_item_stock ADD CONSTRAINT om_item_stock_pkey PRIMARY KEY (id);
ALTER TABLE public.om_order ADD CONSTRAINT om_order_pkey PRIMARY KEY (uuid);
ALTER TABLE public.om_stock_movement ADD CONSTRAINT om_stock_movement_pkey PRIMARY KEY (id);
ALTER TABLE public.om_user ADD CONSTRAINT om_user_pkey PRIMARY KEY (id);

-- Define Unique Constraints
ALTER TABLE public.om_item ADD CONSTRAINT uk_5ylfsw150ew4hd34gn0j4un8x UNIQUE (sku);
ALTER TABLE public.om_item_stock ADD CONSTRAINT uk_i1h2wawkupieuyxkf45ynhml UNIQUE (fk_item);

-- Define Foreign Key Constraints
ALTER TABLE public.om_stock_movement ADD CONSTRAINT fkdex4d1dcuxa2w94xmmitqssva FOREIGN KEY (fk_item) REFERENCES public.om_item(id);
ALTER TABLE public.om_item_stock ADD CONSTRAINT fki70giht6b0fdce7awubqwg2tb FOREIGN KEY (fk_item) REFERENCES public.om_item(id);
ALTER TABLE public.om_order ADD CONSTRAINT fkpxql3hkdcxsvck5wencf778wj FOREIGN KEY (fk_item) REFERENCES public.om_item(id);
ALTER TABLE public.om_order ADD CONSTRAINT fkquouhu3qwwja28k89a28qod7l FOREIGN KEY (fk_user) REFERENCES public.om_user(id);
ALTER TABLE public.om_order_stock_movement ADD CONSTRAINT fkquouhu3qwwja28k89a28qod8l FOREIGN KEY (fk_order) REFERENCES public.om_order(uuid);
ALTER TABLE public.om_order_stock_movement ADD CONSTRAINT fkquouhu3qwwja28k89a28qod9l FOREIGN KEY (fk_stock_movement) REFERENCES public.om_stock_movement(id);
