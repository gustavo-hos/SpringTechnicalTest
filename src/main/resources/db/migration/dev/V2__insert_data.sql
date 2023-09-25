-- Data for Name: om_user; Type: TABLE DATA; Schema: public
INSERT INTO public.om_user (id, email, name) VALUES
 (nextval('om_user_sequence'), 'john.doe@example.com', 'John Doe'),
 (nextval('om_user_sequence'), 'jane.smith@example.com', 'Jane Smith'),
 (nextval('om_user_sequence'), 'michael.johnson@example.com', 'Michael Johnson'),
 (nextval('om_user_sequence'), 'emily.davis@example.com', 'Emily Davis'),
 (nextval('om_user_sequence'), 'william.brown@example.com', 'William Brown');

-- Data for Name: om_item; Type: TABLE DATA; Schema: public
INSERT INTO public.om_item (id, name, sku) VALUES
    (nextval('om_item_sequence'), 'Product A', 'PA001'),
    (nextval('om_item_sequence'), 'Product B', 'PB002'),
    (nextval('om_item_sequence'), 'Product C', 'PC003'),
    (nextval('om_item_sequence'), 'Product D', 'PD004'),
    (nextval('om_item_sequence'), 'Product E', 'PE005');

;