--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.6

-- Started on 2018-02-02 18:06:04

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
-- TOC entry 2177 (class 1262 OID 16384)
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
-- TOC entry 2180 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 189 (class 1259 OID 24626)
-- Name: orders; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE orders (
    id bigint NOT NULL,
    total_cost real DEFAULT 0 NOT NULL,
    user_id bigint NOT NULL,
    status character varying(20) DEFAULT 'OPENED'::character varying NOT NULL
);


ALTER TABLE orders OWNER TO root;

--
-- TOC entry 190 (class 1259 OID 24629)
-- Name: order_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE order_id_seq OWNER TO root;

--
-- TOC entry 2181 (class 0 OID 0)
-- Dependencies: 190
-- Name: order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE order_id_seq OWNED BY orders.id;


--
-- TOC entry 191 (class 1259 OID 24655)
-- Name: order_item; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE order_item (
    product_id bigint NOT NULL,
    quantity integer DEFAULT 0 NOT NULL,
    price real NOT NULL,
    id bigint NOT NULL,
    order_id bigint DEFAULT 1
);


ALTER TABLE order_item OWNER TO root;

--
-- TOC entry 192 (class 1259 OID 24726)
-- Name: order_item_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE order_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE order_item_id_seq OWNER TO root;

--
-- TOC entry 2182 (class 0 OID 0)
-- Dependencies: 192
-- Name: order_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE order_item_id_seq OWNED BY order_item.id;


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
-- TOC entry 2183 (class 0 OID 0)
-- Dependencies: 186
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE product_id_seq OWNED BY product.id;


--
-- TOC entry 187 (class 1259 OID 16407)
-- Name: users; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE users (
    id bigint NOT NULL,
    firstname character varying(30) NOT NULL,
    lastname character varying(30) NOT NULL,
    username character varying(30) NOT NULL,
    password character varying(30) NOT NULL,
    email character varying(30) NOT NULL,
    role character varying(20) DEFAULT 'CUSTOMER'::character varying NOT NULL
);


ALTER TABLE users OWNER TO root;

--
-- TOC entry 188 (class 1259 OID 16410)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_id_seq OWNER TO root;

--
-- TOC entry 2184 (class 0 OID 0)
-- Dependencies: 188
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE user_id_seq OWNED BY users.id;


--
-- TOC entry 2027 (class 2604 OID 24728)
-- Name: order_item id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item ALTER COLUMN id SET DEFAULT nextval('order_item_id_seq'::regclass);


--
-- TOC entry 2023 (class 2604 OID 24631)
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY orders ALTER COLUMN id SET DEFAULT nextval('order_id_seq'::regclass);


--
-- TOC entry 2020 (class 2604 OID 16397)
-- Name: product id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY product ALTER COLUMN id SET DEFAULT nextval('product_id_seq'::regclass);


--
-- TOC entry 2021 (class 2604 OID 16412)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


--
-- TOC entry 2185 (class 0 OID 0)
-- Dependencies: 190
-- Name: order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('order_id_seq', 31, true);


--
-- TOC entry 2171 (class 0 OID 24655)
-- Dependencies: 191
-- Data for Name: order_item; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO order_item (product_id, quantity, price, id, order_id) VALUES (4, 3, 467, 7, 1);
INSERT INTO order_item (product_id, quantity, price, id, order_id) VALUES (36, 2, 36563, 54, 5);


--
-- TOC entry 2186 (class 0 OID 0)
-- Dependencies: 192
-- Name: order_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('order_item_id_seq', 54, true);


--
-- TOC entry 2169 (class 0 OID 24626)
-- Dependencies: 189
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO orders (id, total_cost, user_id, status) VALUES (1, 1111, 1, 'OPENED');
INSERT INTO orders (id, total_cost, user_id, status) VALUES (31, 0, 7, 'OPENED');
INSERT INTO orders (id, total_cost, user_id, status) VALUES (5, 73126, 36, 'OPENED');


--
-- TOC entry 2165 (class 0 OID 16385)
-- Dependencies: 185
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO product (name, brand, description, price, id) VALUES ('iPhone X', 'Apple', 'Display (5.8", OLED (Super Retina HD), 2436x1125)/Apple A11 Bionic/ RAM 3 ГБ/ 3G/ LTE/ GPS/ iOS 11', 44999, 4);
INSERT INTO product (name, brand, description, price, id) VALUES ('Galaxy S8 64GB', 'Samsung', 'Display (5.8", Super AMOLED, 2960х1440)/ Samsung Exynos 8895 (4 x 2.3 ГГц + 4 x 1.7 ГГц)/ 3G/ LTE/ GPS/  Android 7.0 (Nougat) / 3000 мА*h', 22999, 36);
INSERT INTO product (name, brand, description, price, id) VALUES ('Redmi 4X 4/64GB', 'Xiaomi', 'Display (IPS, 1280x720)/ Qualcomm Snapdragon 435 (1.4 ГГц)/ 3G/ LTE/ GPS/ GLONASS/ Android 6.0 (Marshmallow) / 4100 mA*h', 4999, 5);


--
-- TOC entry 2187 (class 0 OID 0)
-- Dependencies: 186
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('product_id_seq', 40, true);


--
-- TOC entry 2188 (class 0 OID 0)
-- Dependencies: 188
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('user_id_seq', 62, true);


--
-- TOC entry 2167 (class 0 OID 16407)
-- Dependencies: 187
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO users (id, firstname, lastname, username, password, email, role) VALUES (7, 'first', 'last', 'test', 'test', 'test@test', 'CUSTOMER');
INSERT INTO users (id, firstname, lastname, username, password, email, role) VALUES (1, 'admin', 'admin', 'admin', 'admin', 'admin@admin', 'ADMIN');
INSERT INTO users (id, firstname, lastname, username, password, email, role) VALUES (8, 'qwerty', 'qwerty', 'qwerty', 'qwerty', 'qwerty', 'ADMIN');
INSERT INTO users (id, firstname, lastname, username, password, email, role) VALUES (36, 'user', 'user', 'user', 'user', 'user@user', 'CUSTOMER');


--
-- TOC entry 2040 (class 2606 OID 24736)
-- Name: order_item order_item_id_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_id_pk PRIMARY KEY (id);


--
-- TOC entry 2037 (class 2606 OID 24633)
-- Name: orders order_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


--
-- TOC entry 2030 (class 2606 OID 16399)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 2032 (class 2606 OID 16414)
-- Name: users user_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY users
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 2035 (class 1259 OID 24654)
-- Name: fki_order_user; Type: INDEX; Schema: public; Owner: root
--

CREATE INDEX fki_order_user ON orders USING btree (user_id);


--
-- TOC entry 2038 (class 1259 OID 24663)
-- Name: fki_product; Type: INDEX; Schema: public; Owner: root
--

CREATE INDEX fki_product ON order_item USING btree (product_id);


--
-- TOC entry 2041 (class 1259 OID 24734)
-- Name: order_item_id_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX order_item_id_uindex ON order_item USING btree (id);


--
-- TOC entry 2033 (class 1259 OID 24818)
-- Name: users_email_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX users_email_uindex ON users USING btree (email);


--
-- TOC entry 2034 (class 1259 OID 24837)
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX users_username_uindex ON users USING btree (username);


--
-- TOC entry 2043 (class 2606 OID 25011)
-- Name: orders fk32ql8ubntj5uh44ph9659tiih; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2047 (class 2606 OID 25006)
-- Name: order_item fk551losx9j75ss5d6bfsqvijna; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT fk551losx9j75ss5d6bfsqvijna FOREIGN KEY (product_id) REFERENCES product(id);


--
-- TOC entry 2042 (class 2606 OID 24649)
-- Name: orders fk_order_user; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2044 (class 2606 OID 24658)
-- Name: order_item fk_product; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id);


--
-- TOC entry 2046 (class 2606 OID 25001)
-- Name: order_item fkt4dc2r9nbvbujrljv3e23iibt; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT fkt4dc2r9nbvbujrljv3e23iibt FOREIGN KEY (order_id) REFERENCES orders(id);


--
-- TOC entry 2045 (class 2606 OID 24996)
-- Name: order_item order_item_orders_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_orders_id_fk FOREIGN KEY (order_id) REFERENCES orders(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2179 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: root
--

GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-02-02 18:06:04

--
-- PostgreSQL database dump complete
--

