--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.6

-- Started on 2018-02-26 18:55:50

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE store;
--
-- TOC entry 2213 (class 1262 OID 16384)
-- Name: store; Type: DATABASE; Schema: -; Owner: root
--

CREATE DATABASE store WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE store OWNER TO root;

\connect store

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2216 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 188 (class 1259 OID 25022)
-- Name: bill; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE bill (
    id bigint NOT NULL,
    card character varying(255) NOT NULL,
    created bigint NOT NULL,
    payed boolean NOT NULL,
    total_cost double precision NOT NULL
);


ALTER TABLE bill OWNER TO root;

--
-- TOC entry 190 (class 1259 OID 25039)
-- Name: order; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE "order" (
    id bigint NOT NULL,
    created bigint NOT NULL,
    total_cost double precision NOT NULL,
    user_id bigint,
    status character varying(30) DEFAULT 'OPENED'::character varying NOT NULL
);


ALTER TABLE "order" OWNER TO root;

--
-- TOC entry 189 (class 1259 OID 25037)
-- Name: customer_order_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE customer_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE customer_order_id_seq OWNER TO root;

--
-- TOC entry 2217 (class 0 OID 0)
-- Dependencies: 189
-- Name: customer_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE customer_order_id_seq OWNED BY "order".id;


--
-- TOC entry 187 (class 1259 OID 24655)
-- Name: order_item; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE order_item (
    product_id bigint NOT NULL,
    quantity integer DEFAULT 0 NOT NULL,
    price real NOT NULL,
    order_id bigint DEFAULT 1 NOT NULL
);


ALTER TABLE order_item OWNER TO root;

--
-- TOC entry 197 (class 1259 OID 25195)
-- Name: permission; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE permission (
    id bigint NOT NULL,
    title character varying(30) NOT NULL
);


ALTER TABLE permission OWNER TO root;

--
-- TOC entry 196 (class 1259 OID 25193)
-- Name: permission_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permission_id_seq OWNER TO root;

--
-- TOC entry 2218 (class 0 OID 0)
-- Dependencies: 196
-- Name: permission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE permission_id_seq OWNED BY permission.id;


--
-- TOC entry 185 (class 1259 OID 16385)
-- Name: product; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE product (
    name character varying(30) NOT NULL,
    brand character varying(30) NOT NULL,
    description character varying(10000) NOT NULL,
    price real NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE product OWNER TO root;

--
-- TOC entry 186 (class 1259 OID 16395)
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE product_id_seq OWNER TO root;

--
-- TOC entry 2219 (class 0 OID 0)
-- Dependencies: 186
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE product_id_seq OWNED BY product.id;


--
-- TOC entry 192 (class 1259 OID 25047)
-- Name: role; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE role (
    id bigint NOT NULL,
    title character varying(30) NOT NULL
);


ALTER TABLE role OWNER TO root;

--
-- TOC entry 191 (class 1259 OID 25045)
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE role_id_seq OWNER TO root;

--
-- TOC entry 2220 (class 0 OID 0)
-- Dependencies: 191
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE role_id_seq OWNED BY role.id;


--
-- TOC entry 194 (class 1259 OID 25055)
-- Name: user; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE "user" (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE "user" OWNER TO root;

--
-- TOC entry 193 (class 1259 OID 25053)
-- Name: user_account_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE user_account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_account_id_seq OWNER TO root;

--
-- TOC entry 2221 (class 0 OID 0)
-- Dependencies: 193
-- Name: user_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE user_account_id_seq OWNED BY "user".id;


--
-- TOC entry 198 (class 1259 OID 25202)
-- Name: user_permission; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE user_permission (
    user_id bigint NOT NULL,
    permission_id bigint NOT NULL
);


ALTER TABLE user_permission OWNER TO root;

--
-- TOC entry 195 (class 1259 OID 25108)
-- Name: user_role; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE user_role (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE user_role OWNER TO root;

--
-- TOC entry 2046 (class 2604 OID 25042)
-- Name: order id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY "order" ALTER COLUMN id SET DEFAULT nextval('customer_order_id_seq'::regclass);


--
-- TOC entry 2050 (class 2604 OID 25198)
-- Name: permission id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY permission ALTER COLUMN id SET DEFAULT nextval('permission_id_seq'::regclass);


--
-- TOC entry 2043 (class 2604 OID 16397)
-- Name: product id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY product ALTER COLUMN id SET DEFAULT nextval('product_id_seq'::regclass);


--
-- TOC entry 2048 (class 2604 OID 25050)
-- Name: role id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY role ALTER COLUMN id SET DEFAULT nextval('role_id_seq'::regclass);


--
-- TOC entry 2049 (class 2604 OID 25058)
-- Name: user id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_account_id_seq'::regclass);


--
-- TOC entry 2198 (class 0 OID 25022)
-- Dependencies: 188
-- Data for Name: bill; Type: TABLE DATA; Schema: public; Owner: root
--



--
-- TOC entry 2222 (class 0 OID 0)
-- Dependencies: 189
-- Name: customer_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('customer_order_id_seq', 1, true);


--
-- TOC entry 2200 (class 0 OID 25039)
-- Dependencies: 190
-- Data for Name: order; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO "order" (id, created, total_cost, user_id, status) VALUES (1, 1518739200000, 0, 2, 'OPENED');


--
-- TOC entry 2197 (class 0 OID 24655)
-- Dependencies: 187
-- Data for Name: order_item; Type: TABLE DATA; Schema: public; Owner: root
--



--
-- TOC entry 2207 (class 0 OID 25195)
-- Dependencies: 197
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO permission (id, title) VALUES (1, 'SUPERVISOR');
INSERT INTO permission (id, title) VALUES (2, 'ORDER_READ_ALL');
INSERT INTO permission (id, title) VALUES (3, 'ORDER_UPDATE_ALL');
INSERT INTO permission (id, title) VALUES (4, 'ORDER_DELETE_SAME');


--
-- TOC entry 2223 (class 0 OID 0)
-- Dependencies: 196
-- Name: permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('permission_id_seq', 4, true);


--
-- TOC entry 2195 (class 0 OID 16385)
-- Dependencies: 185
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO product (name, brand, description, price, id) VALUES ('iPhone X', 'Apple', 'Display (5.8", OLED (Super Retina HD), 2436x1125)/Apple A11 Bionic/ RAM 3 ГБ/ 3G/ LTE/ GPS/ iOS 11', 44999, 4);
INSERT INTO product (name, brand, description, price, id) VALUES ('Galaxy S8 64GB', 'Samsung', 'Display (5.8", Super AMOLED, 2960х1440)/ Samsung Exynos 8895 (4 x 2.3 ГГц + 4 x 1.7 ГГц)/ 3G/ LTE/ GPS/  Android 7.0 (Nougat) / 3000 мА*h', 22999, 36);
INSERT INTO product (name, brand, description, price, id) VALUES ('Test', 'Test', 'Some description', 123, 47);
INSERT INTO product (name, brand, description, price, id) VALUES ('TestTestTestTe', 'Test', 'test', 0, 48);
INSERT INTO product (name, brand, description, price, id) VALUES ('Redmi 4X 4/64GB', 'Xiaomi', 'Display (IPS, 1280x720)/ Qualcomm Snapdragon 435 (1.4 ГГц)/ 3G/ LTE/ GPS/ GLONASS/ Android 6.0 (Marshmallow) / 4100 mA*h', 4999, 5);
INSERT INTO product (name, brand, description, price, id) VALUES ('TestTestTestTestTestTestTest', 'Test', 'test', 111, 44);


--
-- TOC entry 2224 (class 0 OID 0)
-- Dependencies: 186
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('product_id_seq', 49, true);


--
-- TOC entry 2202 (class 0 OID 25047)
-- Dependencies: 192
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO role (id, title) VALUES (1, 'ADMIN');
INSERT INTO role (id, title) VALUES (2, 'CUSTOMER');


--
-- TOC entry 2225 (class 0 OID 0)
-- Dependencies: 191
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('role_id_seq', 2, true);


--
-- TOC entry 2204 (class 0 OID 25055)
-- Dependencies: 194
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO "user" (id, email, enabled, first_name, last_name, password, username) VALUES (1, 'admin@admin.com', true, 'admin', 'admin', 'admin', 'admin');
INSERT INTO "user" (id, email, enabled, first_name, last_name, password, username) VALUES (2, 'user@user.com', true, 'user', 'user', 'user', 'user');


--
-- TOC entry 2226 (class 0 OID 0)
-- Dependencies: 193
-- Name: user_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('user_account_id_seq', 2, true);


--
-- TOC entry 2208 (class 0 OID 25202)
-- Dependencies: 198
-- Data for Name: user_permission; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO user_permission (user_id, permission_id) VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 3);
INSERT INTO user_permission (user_id, permission_id) VALUES (1, 4);


--
-- TOC entry 2205 (class 0 OID 25108)
-- Dependencies: 195
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);


--
-- TOC entry 2057 (class 2606 OID 25026)
-- Name: bill bill_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY bill
    ADD CONSTRAINT bill_pkey PRIMARY KEY (id);


--
-- TOC entry 2055 (class 2606 OID 25129)
-- Name: order_item order_item_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_pk PRIMARY KEY (product_id, order_id);


--
-- TOC entry 2059 (class 2606 OID 25131)
-- Name: order order_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_pk PRIMARY KEY (id);


--
-- TOC entry 2068 (class 2606 OID 25200)
-- Name: permission permission_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id);


--
-- TOC entry 2052 (class 2606 OID 16399)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 2061 (class 2606 OID 25052)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 2063 (class 2606 OID 25063)
-- Name: user user_account_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_account_pkey PRIMARY KEY (id);


--
-- TOC entry 2070 (class 2606 OID 25208)
-- Name: user_permission user_permission_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY user_permission
    ADD CONSTRAINT user_permission_pk PRIMARY KEY (user_id, permission_id);


--
-- TOC entry 2065 (class 2606 OID 25112)
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 2053 (class 1259 OID 24663)
-- Name: fki_product; Type: INDEX; Schema: public; Owner: root
--

CREATE INDEX fki_product ON order_item USING btree (product_id);


--
-- TOC entry 2066 (class 1259 OID 25201)
-- Name: permission_id_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX permission_id_uindex ON permission USING btree (id);


--
-- TOC entry 2072 (class 2606 OID 25137)
-- Name: order_item order_item_order_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_order_id_fk FOREIGN KEY (order_id) REFERENCES "order"(id);


--
-- TOC entry 2071 (class 2606 OID 25219)
-- Name: order_item order_item_product_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_product_id_fk FOREIGN KEY (product_id) REFERENCES product(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2073 (class 2606 OID 25154)
-- Name: order order_user_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2077 (class 2606 OID 25214)
-- Name: user_permission user_permission_permission_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY user_permission
    ADD CONSTRAINT user_permission_permission_id_fk FOREIGN KEY (permission_id) REFERENCES permission(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2076 (class 2606 OID 25209)
-- Name: user_permission user_permission_user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY user_permission
    ADD CONSTRAINT user_permission_user_id_fk FOREIGN KEY (user_id) REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2075 (class 2606 OID 25164)
-- Name: user_role user_role_role_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_role_fk FOREIGN KEY (role_id) REFERENCES role(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2074 (class 2606 OID 25159)
-- Name: user_role user_role_user_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2215 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: root
--

GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-02-26 18:55:51

--
-- PostgreSQL database dump complete
--

