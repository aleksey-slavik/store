--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.6

-- Started on 2018-01-19 19:37:40

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
-- TOC entry 2214 (class 1262 OID 16384)
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
-- TOC entry 2217 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 198 (class 1259 OID 24784)
-- Name: order_details; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE order_details (
    id bigint NOT NULL,
    order_item_id bigint NOT NULL,
    order_id bigint NOT NULL
);


ALTER TABLE order_details OWNER TO root;

--
-- TOC entry 197 (class 1259 OID 24782)
-- Name: order_details_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE order_details_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE order_details_id_seq OWNER TO root;

--
-- TOC entry 2218 (class 0 OID 0)
-- Dependencies: 197
-- Name: order_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE order_details_id_seq OWNED BY order_details.id;


--
-- TOC entry 189 (class 1259 OID 24626)
-- Name: orders; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE orders (
    id bigint NOT NULL,
    total_cost real DEFAULT 0 NOT NULL,
    user_id bigint NOT NULL,
    status_id bigint DEFAULT 1 NOT NULL
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
-- TOC entry 2219 (class 0 OID 0)
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
    id bigint NOT NULL
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
-- TOC entry 2220 (class 0 OID 0)
-- Dependencies: 192
-- Name: order_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE order_item_id_seq OWNED BY order_item.id;


--
-- TOC entry 196 (class 1259 OID 24774)
-- Name: order_status; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE order_status (
    id bigint NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE order_status OWNER TO root;

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
-- TOC entry 2221 (class 0 OID 0)
-- Dependencies: 186
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE product_id_seq OWNED BY product.id;


--
-- TOC entry 195 (class 1259 OID 24772)
-- Name: status_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE status_id_seq OWNER TO root;

--
-- TOC entry 2222 (class 0 OID 0)
-- Dependencies: 195
-- Name: status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE status_id_seq OWNED BY order_status.id;


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
    role_id bigint DEFAULT 1 NOT NULL
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
-- TOC entry 2223 (class 0 OID 0)
-- Dependencies: 188
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE user_id_seq OWNED BY users.id;


--
-- TOC entry 194 (class 1259 OID 24764)
-- Name: user_role; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE user_role (
    id bigint NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE user_role OWNER TO root;

--
-- TOC entry 193 (class 1259 OID 24762)
-- Name: userrole_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE userrole_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE userrole_id_seq OWNER TO root;

--
-- TOC entry 2224 (class 0 OID 0)
-- Dependencies: 193
-- Name: userrole_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE userrole_id_seq OWNED BY user_role.id;


--
-- TOC entry 2048 (class 2604 OID 24787)
-- Name: order_details id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_details ALTER COLUMN id SET DEFAULT nextval('order_details_id_seq'::regclass);


--
-- TOC entry 2045 (class 2604 OID 24728)
-- Name: order_item id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item ALTER COLUMN id SET DEFAULT nextval('order_item_id_seq'::regclass);


--
-- TOC entry 2047 (class 2604 OID 24777)
-- Name: order_status id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_status ALTER COLUMN id SET DEFAULT nextval('status_id_seq'::regclass);


--
-- TOC entry 2041 (class 2604 OID 24631)
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY orders ALTER COLUMN id SET DEFAULT nextval('order_id_seq'::regclass);


--
-- TOC entry 2038 (class 2604 OID 16397)
-- Name: product id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY product ALTER COLUMN id SET DEFAULT nextval('product_id_seq'::regclass);


--
-- TOC entry 2046 (class 2604 OID 24767)
-- Name: user_role id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY user_role ALTER COLUMN id SET DEFAULT nextval('userrole_id_seq'::regclass);


--
-- TOC entry 2039 (class 2604 OID 16412)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


--
-- TOC entry 2209 (class 0 OID 24784)
-- Dependencies: 198
-- Data for Name: order_details; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO order_details (id, order_item_id, order_id) VALUES (1, 7, 5);
INSERT INTO order_details (id, order_item_id, order_id) VALUES (2, 12, 1);


--
-- TOC entry 2225 (class 0 OID 0)
-- Dependencies: 197
-- Name: order_details_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('order_details_id_seq', 3, true);


--
-- TOC entry 2226 (class 0 OID 0)
-- Dependencies: 190
-- Name: order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('order_id_seq', 22, true);


--
-- TOC entry 2202 (class 0 OID 24655)
-- Dependencies: 191
-- Data for Name: order_item; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO order_item (product_id, quantity, price, id) VALUES (5, 1, 398745, 12);
INSERT INTO order_item (product_id, quantity, price, id) VALUES (4, 3, 467, 7);
INSERT INTO order_item (product_id, quantity, price, id) VALUES (4, 7, 666, 27);
INSERT INTO order_item (product_id, quantity, price, id) VALUES (36, 12, 777, 28);


--
-- TOC entry 2227 (class 0 OID 0)
-- Dependencies: 192
-- Name: order_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('order_item_id_seq', 28, true);


--
-- TOC entry 2207 (class 0 OID 24774)
-- Dependencies: 196
-- Data for Name: order_status; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO order_status (id, name) VALUES (1, 'OPENED');
INSERT INTO order_status (id, name) VALUES (2, 'PAID');


--
-- TOC entry 2200 (class 0 OID 24626)
-- Dependencies: 189
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO orders (id, total_cost, user_id, status_id) VALUES (1, 1111, 1, 1);
INSERT INTO orders (id, total_cost, user_id, status_id) VALUES (5, 86876, 36, 1);


--
-- TOC entry 2196 (class 0 OID 16385)
-- Dependencies: 185
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO product (name, brand, description, price, id) VALUES ('iPhone X', 'Apple', 'Display (5.8", OLED (Super Retina HD), 2436x1125)/Apple A11 Bionic/ RAM 3 ГБ/ 3G/ LTE/ GPS/ iOS 11', 44999, 4);
INSERT INTO product (name, brand, description, price, id) VALUES ('Galaxy S8 64GB', 'Samsung', 'Display (5.8", Super AMOLED, 2960х1440)/ Samsung Exynos 8895 (4 x 2.3 ГГц + 4 x 1.7 ГГц)/ / 3G/ LTE/ GPS/  Android 7.0 (Nougat) / 3000 мА*h', 22999, 36);
INSERT INTO product (name, brand, description, price, id) VALUES ('Redmi 4X 4/64GB', 'Xiaomi', 'Display (IPS, 1280x720)/ Qualcomm Snapdragon 435 (1.4 ГГц)/ 3G/ LTE/ GPS/ GLONASS/ Android 6.0 (Marshmallow) / 4100 mA*h', 4999, 5);


--
-- TOC entry 2228 (class 0 OID 0)
-- Dependencies: 186
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('product_id_seq', 36, true);


--
-- TOC entry 2229 (class 0 OID 0)
-- Dependencies: 195
-- Name: status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('status_id_seq', 2, true);


--
-- TOC entry 2230 (class 0 OID 0)
-- Dependencies: 188
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('user_id_seq', 61, true);


--
-- TOC entry 2205 (class 0 OID 24764)
-- Dependencies: 194
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO user_role (id, name) VALUES (1, 'CUSTOMER');
INSERT INTO user_role (id, name) VALUES (2, 'ADMIN');


--
-- TOC entry 2231 (class 0 OID 0)
-- Dependencies: 193
-- Name: userrole_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('userrole_id_seq', 4, true);


--
-- TOC entry 2198 (class 0 OID 16407)
-- Dependencies: 187
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO users (id, firstname, lastname, username, password, email, role_id) VALUES (7, 'first', 'last', 'test', 'test', 'test@test', 1);
INSERT INTO users (id, firstname, lastname, username, password, email, role_id) VALUES (8, 'qwerty', 'qwerty', 'qwerty', 'qwerty', 'qwerty', 1);
INSERT INTO users (id, firstname, lastname, username, password, email, role_id) VALUES (56, '111', '111', '111', '111', '111', 1);
INSERT INTO users (id, firstname, lastname, username, password, email, role_id) VALUES (58, '111', '111', '1111', '111', '1111', 1);
INSERT INTO users (id, firstname, lastname, username, password, email, role_id) VALUES (1, 'admin', 'admin', 'admin', 'admin', 'admin@admin', 2);
INSERT INTO users (id, firstname, lastname, username, password, email, role_id) VALUES (36, 'user', 'user', 'user', 'user', 'user@user', 1);


--
-- TOC entry 2072 (class 2606 OID 24789)
-- Name: order_details order_details_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_details
    ADD CONSTRAINT order_details_pkey PRIMARY KEY (id);


--
-- TOC entry 2060 (class 2606 OID 24736)
-- Name: order_item order_item_id_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_id_pk PRIMARY KEY (id);


--
-- TOC entry 2057 (class 2606 OID 24633)
-- Name: orders order_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


--
-- TOC entry 2050 (class 2606 OID 16399)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 2069 (class 2606 OID 24779)
-- Name: order_status status_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_status
    ADD CONSTRAINT status_pkey PRIMARY KEY (id);


--
-- TOC entry 2052 (class 2606 OID 16414)
-- Name: users user_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY users
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 2065 (class 2606 OID 24769)
-- Name: user_role userrole_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT userrole_pkey PRIMARY KEY (id);


--
-- TOC entry 2055 (class 1259 OID 24654)
-- Name: fki_order_user; Type: INDEX; Schema: public; Owner: root
--

CREATE INDEX fki_order_user ON orders USING btree (user_id);


--
-- TOC entry 2058 (class 1259 OID 24663)
-- Name: fki_product; Type: INDEX; Schema: public; Owner: root
--

CREATE INDEX fki_product ON order_item USING btree (product_id);


--
-- TOC entry 2070 (class 1259 OID 24790)
-- Name: order_details_id_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX order_details_id_uindex ON order_details USING btree (id);


--
-- TOC entry 2061 (class 1259 OID 24734)
-- Name: order_item_id_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX order_item_id_uindex ON order_item USING btree (id);


--
-- TOC entry 2066 (class 1259 OID 24780)
-- Name: status_id_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX status_id_uindex ON order_status USING btree (id);


--
-- TOC entry 2067 (class 1259 OID 24781)
-- Name: status_name_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX status_name_uindex ON order_status USING btree (name);


--
-- TOC entry 2062 (class 1259 OID 24770)
-- Name: userrole_id_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX userrole_id_uindex ON user_role USING btree (id);


--
-- TOC entry 2063 (class 1259 OID 24771)
-- Name: userrole_name_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX userrole_name_uindex ON user_role USING btree (name);


--
-- TOC entry 2053 (class 1259 OID 24818)
-- Name: users_email_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX users_email_uindex ON users USING btree (email);


--
-- TOC entry 2054 (class 1259 OID 24837)
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX users_username_uindex ON users USING btree (username);


--
-- TOC entry 2074 (class 2606 OID 24649)
-- Name: orders fk_order_user; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2076 (class 2606 OID 24658)
-- Name: order_item fk_product; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id);


--
-- TOC entry 2078 (class 2606 OID 24914)
-- Name: order_details order_details_order_item_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_details
    ADD CONSTRAINT order_details_order_item_id_fk FOREIGN KEY (order_item_id) REFERENCES order_item(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2077 (class 2606 OID 24909)
-- Name: order_details order_details_orders_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_details
    ADD CONSTRAINT order_details_orders_id_fk FOREIGN KEY (order_id) REFERENCES orders(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2075 (class 2606 OID 24860)
-- Name: orders orders_status_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_status_id_fk FOREIGN KEY (status_id) REFERENCES order_status(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2073 (class 2606 OID 24805)
-- Name: users users_user_role_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_user_role_id_fk FOREIGN KEY (role_id) REFERENCES user_role(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2216 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: root
--

GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-01-19 19:37:40

--
-- PostgreSQL database dump complete
--

