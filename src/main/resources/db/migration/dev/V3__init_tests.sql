
-- Data for Name: om_user; Type: TABLE DATA; Schema: public
INSERT INTO public.om_user (id, email, name) VALUES
    (nextval('om_user_sequence'), 'nico.hemmer@example.com', 'Nico Hemmer'),
    (nextval('om_user_sequence'), 'stephan.rocker@example.com', 'Stephan Rocker'),
    (nextval('om_user_sequence'), 'steve.bronks@example.com', 'Steve Bronks'),
    (nextval('om_user_sequence'), 'dayna.marie@example.com', 'Dayna Marie'),
    (nextval('om_user_sequence'), 'joana.hills@example.com', 'Joana Hills');

-- Data for Name: om_item; Type: TABLE DATA; Schema: public
INSERT INTO public.om_item (id, name, sku) VALUES
    (nextval('om_item_sequence'), 'Product F', 'PF006'),
    (nextval('om_item_sequence'), 'Product G', 'PG007'),
    (nextval('om_item_sequence'), 'Product H', 'PH008'),
    (nextval('om_item_sequence'), 'Product I', 'PI009'),
    (nextval('om_item_sequence'), 'Product J', 'PJ010');

;

-- Insert random data into the om_order table
INSERT INTO public.om_order (uuid, quantity, supplied, status, fk_item, fk_user, creation_date)
VALUES
    ('81387eea-03c8-4f8e-861d-62feace28299', 8,  0,'PENDING',   5, 1, '2023-09-22T04:54:00.142+00:00'),
    ('b3fff179-15d5-4059-90ad-2dc1df86e073', 3,  0,'PENDING',   3, 5, '2023-09-22T02:22:00.142+00:00'),
    ('955d85aa-0e5f-40e5-ba8c-19f7ff2f1281', 22, 0, 'COMPLETED', 4, 8, '2023-09-22T01:36:00.142+00:00'),
    ('5015e7e9-7802-47db-a943-0dffa2772f8e', 65, 0, 'COMPLETED', 8, 2, '2023-09-22T23:42:00.142+00:00'),
    ('023d3edc-7606-4c16-b7e8-b4a3e47344f2', 12, 0, 'PENDING',   9, 6, '2023-09-22T16:47:00.142+00:00'),
    ('02150b93-a6bf-44c1-96a4-d59a6505c9a3', 13, 0, 'COMPLETED', 4, 7, '2023-09-22T15:14:00.142+00:00'),
    ('78d27e85-1c07-47fb-8cab-1fdd2502804d', 42, 0, 'COMPLETED', 2, 2, '2023-09-22T18:09:00.142+00:00'),
    ('e28eec66-9d25-4e6d-8cc8-1fee651d20d3', 8, 0, 'PENDING',   6, 4, '2023-09-22T17:28:00.142+00:00');
