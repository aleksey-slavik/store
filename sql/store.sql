--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.6

-- Started on 2018-03-22 14:35:23

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
-- TOC entry 2266 (class 1262 OID 16384)
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
-- TOC entry 2269 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 199 (class 1259 OID 25236)
-- Name: acl_class; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE acl_class (
    id bigint NOT NULL,
    class character varying(100) NOT NULL
);


ALTER TABLE acl_class OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 25234)
-- Name: acl_class_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE acl_class_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE acl_class_id_seq OWNER TO postgres;

--
-- TOC entry 2270 (class 0 OID 0)
-- Dependencies: 198
-- Name: acl_class_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE acl_class_id_seq OWNED BY acl_class.id;


--
-- TOC entry 203 (class 1259 OID 25271)
-- Name: acl_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE acl_entry (
    id bigint NOT NULL,
    acl_object_identity bigint NOT NULL,
    ace_order integer NOT NULL,
    sid bigint NOT NULL,
    mask integer NOT NULL,
    granting boolean NOT NULL,
    audit_success boolean NOT NULL,
    audit_failure boolean NOT NULL
);


ALTER TABLE acl_entry OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 25269)
-- Name: acl_entry_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE acl_entry_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE acl_entry_id_seq OWNER TO postgres;

--
-- TOC entry 2271 (class 0 OID 0)
-- Dependencies: 202
-- Name: acl_entry_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE acl_entry_id_seq OWNED BY acl_entry.id;


--
-- TOC entry 201 (class 1259 OID 25246)
-- Name: acl_object_identity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE acl_object_identity (
    id bigint NOT NULL,
    object_id_class bigint NOT NULL,
    object_id_identity bigint NOT NULL,
    parent_object bigint,
    owner_sid bigint,
    entries_inheriting boolean NOT NULL
);


ALTER TABLE acl_object_identity OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 25244)
-- Name: acl_object_identity_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE acl_object_identity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE acl_object_identity_id_seq OWNER TO postgres;

--
-- TOC entry 2272 (class 0 OID 0)
-- Dependencies: 200
-- Name: acl_object_identity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE acl_object_identity_id_seq OWNED BY acl_object_identity.id;


--
-- TOC entry 197 (class 1259 OID 25226)
-- Name: acl_sid; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE acl_sid (
    id bigint NOT NULL,
    principal boolean NOT NULL,
    sid character varying(100) NOT NULL
);


ALTER TABLE acl_sid OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 25224)
-- Name: acl_sid_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE acl_sid_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE acl_sid_id_seq OWNER TO postgres;

--
-- TOC entry 2273 (class 0 OID 0)
-- Dependencies: 196
-- Name: acl_sid_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE acl_sid_id_seq OWNED BY acl_sid.id;


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
-- TOC entry 2274 (class 0 OID 0)
-- Dependencies: 189
-- Name: customer_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE customer_order_id_seq OWNED BY "order".id;


--
-- TOC entry 204 (class 1259 OID 25355)
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
-- TOC entry 2275 (class 0 OID 0)
-- Dependencies: 204
-- Name: order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE order_id_seq OWNED BY "order".id;


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
-- TOC entry 206 (class 1259 OID 25360)
-- Name: permission; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE permission (
    id integer NOT NULL,
    object_class character varying(256) NOT NULL,
    object_id bigint NOT NULL,
    sid character varying(30) NOT NULL,
    permission character varying(30) NOT NULL
);


ALTER TABLE permission OWNER TO root;

--
-- TOC entry 205 (class 1259 OID 25358)
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
-- TOC entry 2276 (class 0 OID 0)
-- Dependencies: 205
-- Name: permission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE permission_id_seq OWNED BY permission.id;


--
-- TOC entry 185 (class 1259 OID 16385)
-- Name: product; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE product (
    name character varying(30) NOT NULL,
    description character varying(10000) NOT NULL,
    price real NOT NULL,
    id bigint NOT NULL,
    brand character varying(100),
    owner_id bigint NOT NULL
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
-- TOC entry 2277 (class 0 OID 0)
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
-- TOC entry 2278 (class 0 OID 0)
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
-- TOC entry 2279 (class 0 OID 0)
-- Dependencies: 193
-- Name: user_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE user_account_id_seq OWNED BY "user".id;


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
-- TOC entry 2073 (class 2604 OID 25239)
-- Name: acl_class id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_class ALTER COLUMN id SET DEFAULT nextval('acl_class_id_seq'::regclass);


--
-- TOC entry 2075 (class 2604 OID 25274)
-- Name: acl_entry id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_entry ALTER COLUMN id SET DEFAULT nextval('acl_entry_id_seq'::regclass);


--
-- TOC entry 2074 (class 2604 OID 25249)
-- Name: acl_object_identity id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_object_identity ALTER COLUMN id SET DEFAULT nextval('acl_object_identity_id_seq'::regclass);


--
-- TOC entry 2072 (class 2604 OID 25229)
-- Name: acl_sid id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_sid ALTER COLUMN id SET DEFAULT nextval('acl_sid_id_seq'::regclass);


--
-- TOC entry 2069 (class 2604 OID 25357)
-- Name: order id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY "order" ALTER COLUMN id SET DEFAULT nextval('order_id_seq'::regclass);


--
-- TOC entry 2076 (class 2604 OID 25363)
-- Name: permission id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY permission ALTER COLUMN id SET DEFAULT nextval('permission_id_seq'::regclass);


--
-- TOC entry 2065 (class 2604 OID 16397)
-- Name: product id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY product ALTER COLUMN id SET DEFAULT nextval('product_id_seq'::regclass);


--
-- TOC entry 2070 (class 2604 OID 25050)
-- Name: role id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY role ALTER COLUMN id SET DEFAULT nextval('role_id_seq'::regclass);


--
-- TOC entry 2071 (class 2604 OID 25058)
-- Name: user id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_account_id_seq'::regclass);


--
-- TOC entry 2254 (class 0 OID 25236)
-- Dependencies: 199
-- Data for Name: acl_class; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO acl_class (id, class) VALUES (1, 'com.globallogic.store.domain.product.Product');


--
-- TOC entry 2280 (class 0 OID 0)
-- Dependencies: 198
-- Name: acl_class_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('acl_class_id_seq', 1, true);


--
-- TOC entry 2258 (class 0 OID 25271)
-- Dependencies: 203
-- Data for Name: acl_entry; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (235, 2, 0, 1, 16, true, false, false);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (236, 2, 1, 1, 2, true, false, false);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (237, 2, 2, 2, 2, true, false, false);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (241, 4, 0, 1, 16, true, false, false);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (242, 4, 1, 2, 2, true, false, false);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (91, 3, 0, 1, 16, true, false, false);


--
-- TOC entry 2281 (class 0 OID 0)
-- Dependencies: 202
-- Name: acl_entry_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('acl_entry_id_seq', 242, true);


--
-- TOC entry 2256 (class 0 OID 25246)
-- Dependencies: 201
-- Data for Name: acl_object_identity; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (4, 1, 62, NULL, 1, true);
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (1, 1, 36, NULL, 1, true);
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (3, 1, 59, NULL, 1, true);
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (2, 1, 58, NULL, 2, true);


--
-- TOC entry 2282 (class 0 OID 0)
-- Dependencies: 200
-- Name: acl_object_identity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('acl_object_identity_id_seq', 4, true);


--
-- TOC entry 2252 (class 0 OID 25226)
-- Dependencies: 197
-- Data for Name: acl_sid; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO acl_sid (id, principal, sid) VALUES (1, true, 'user');
INSERT INTO acl_sid (id, principal, sid) VALUES (2, true, 'test');


--
-- TOC entry 2283 (class 0 OID 0)
-- Dependencies: 196
-- Name: acl_sid_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('acl_sid_id_seq', 2, true);


--
-- TOC entry 2243 (class 0 OID 25022)
-- Dependencies: 188
-- Data for Name: bill; Type: TABLE DATA; Schema: public; Owner: root
--



--
-- TOC entry 2284 (class 0 OID 0)
-- Dependencies: 189
-- Name: customer_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('customer_order_id_seq', 1, true);


--
-- TOC entry 2245 (class 0 OID 25039)
-- Dependencies: 190
-- Data for Name: order; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO "order" (id, created, total_cost, user_id, status) VALUES (1, 1521197520000, 95996, 2, 'PAID');
INSERT INTO "order" (id, created, total_cost, user_id, status) VALUES (5, 1521198750743, 44999, 2, 'OPENED');
INSERT INTO "order" (id, created, total_cost, user_id, status) VALUES (7, 1521198809288, 45998, 4, 'OPENED');
INSERT INTO "order" (id, created, total_cost, user_id, status) VALUES (8, 1521711682246, 44999, 18, 'PAID');


--
-- TOC entry 2285 (class 0 OID 0)
-- Dependencies: 204
-- Name: order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('order_id_seq', 8, true);


--
-- TOC entry 2242 (class 0 OID 24655)
-- Dependencies: 187
-- Data for Name: order_item; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO order_item (product_id, quantity, price, order_id) VALUES (4, 1, 44999, 1);
INSERT INTO order_item (product_id, quantity, price, order_id) VALUES (5, 1, 4999, 1);
INSERT INTO order_item (product_id, quantity, price, order_id) VALUES (36, 2, 22999, 1);
INSERT INTO order_item (product_id, quantity, price, order_id) VALUES (4, 1, 44999, 5);
INSERT INTO order_item (product_id, quantity, price, order_id) VALUES (36, 2, 22999, 7);
INSERT INTO order_item (product_id, quantity, price, order_id) VALUES (4, 1, 44999, 8);


--
-- TOC entry 2261 (class 0 OID 25360)
-- Dependencies: 206
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO permission (id, object_class, object_id, sid, permission) VALUES (4, 'com.globallogic.store.domain.product.Product', 65, 'test', 'ADMINISTRATION');
INSERT INTO permission (id, object_class, object_id, sid, permission) VALUES (5, 'com.globallogic.store.domain.product.Product', 4, 'user', 'ADMINISTRATION');
INSERT INTO permission (id, object_class, object_id, sid, permission) VALUES (6, 'com.globallogic.store.domain.product.Product', 5, 'user', 'ADMINISTRATION');
INSERT INTO permission (id, object_class, object_id, sid, permission) VALUES (7, 'com.globallogic.store.domain.product.Product', 36, 'user', 'ADMINISTRATION');
INSERT INTO permission (id, object_class, object_id, sid, permission) VALUES (8, 'com.globallogic.store.domain.product.Product', 66, 'root', 'ADMINISTRATION');
INSERT INTO permission (id, object_class, object_id, sid, permission) VALUES (14, 'com.globallogic.store.domain.product.Product', 4, 'test', 'UPDATE');


--
-- TOC entry 2286 (class 0 OID 0)
-- Dependencies: 205
-- Name: permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('permission_id_seq', 14, true);


--
-- TOC entry 2240 (class 0 OID 16385)
-- Dependencies: 185
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO product (name, description, price, id, brand, owner_id) VALUES ('Redmi 4X 4/64GB', 'Display (IPS, 1280x720)/ Qualcomm Snapdragon 435 (1.4 ГГц)/ 3G/ LTE/ GPS/ GLONASS/ Android 6.0 (Marshmallow) / 4100 mA*h', 4999, 5, 'Xiaomi', 2);
INSERT INTO product (name, description, price, id, brand, owner_id) VALUES ('Galaxy S8 64GB', 'Display (5.8", Super AMOLED, 2960х1440)/ Samsung Exynos 8895 (4 x 2.3 ГГц + 4 x 1.7 ГГц)/ 3G/ LTE/ GPS/  Android 7.0 (Nougat) / 3000 мА*h', 22999, 36, 'Samsung', 2);
INSERT INTO product (name, description, price, id, brand, owner_id) VALUES ('K6 Note', 'Screen (5.5 ", IPS, 1920x1080) / Qualcomm Snapdragon 430 (1.4 GHz) / The basic camera is 16 megapixels, frontalnaya camera: 8 MP / RAM 3 GB / 32 GB vstroennoy memory + microSD / SDHC (up to 128GB) / 3G / LTE / GPS / support for 2 SIM cards (Nano-SIM) / Android 6.0 (Marshmallow) / 4000 mAh', 6495, 65, 'Lenovo', 4);
INSERT INTO product (name, description, price, id, brand, owner_id) VALUES ('Moto Z2 Force', 'Screen (5.5 ", Super AMOLED, 2560x1440) / Qualcomm Snapdragon 835 (2.35 GHz) / main camera: 12 Mp + 12 Mp, front camera: 5 Mp / RAM 6 GB / 64 GB internal memory + microSD / SDHC (up to 2 TB ) / 3G / LTE / GPS / support for 2 SIM cards (Nano-SIM) / Android 7.1 (Nougat) / 2730 mAh', 17995, 66, 'Motorola', 18);
INSERT INTO product (name, description, price, id, brand, owner_id) VALUES ('iPhone X', 'Display (5.8", OLED (Super Retina HD), 2436x1125)/Apple A11 Bionic/ RAM 3 ГБ/ 3G/ LTE/ GPS/ iOS 11', 44999, 4, 'Apple', 2);


--
-- TOC entry 2287 (class 0 OID 0)
-- Dependencies: 186
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('product_id_seq', 66, true);


--
-- TOC entry 2247 (class 0 OID 25047)
-- Dependencies: 192
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO role (id, title) VALUES (1, 'ADMIN');
INSERT INTO role (id, title) VALUES (2, 'CUSTOMER');


--
-- TOC entry 2288 (class 0 OID 0)
-- Dependencies: 191
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('role_id_seq', 3, true);


--
-- TOC entry 2249 (class 0 OID 25055)
-- Dependencies: 194
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO "user" (id, email, enabled, first_name, last_name, password, username) VALUES (1, 'admin@admin.com', true, 'admin', 'admin', 'admin', 'admin');
INSERT INTO "user" (id, email, enabled, first_name, last_name, password, username) VALUES (5, 'moderator@test.com', true, 'mode', 'rator', 'moderator', 'moderator');
INSERT INTO "user" (id, email, enabled, first_name, last_name, password, username) VALUES (4, 'test@test.com', true, 'test', 'test', 'test', 'test');
INSERT INTO "user" (id, email, enabled, first_name, last_name, password, username) VALUES (18, 'root@mail.com', true, 'root', 'root', 'root', 'root');
INSERT INTO "user" (id, email, enabled, first_name, last_name, password, username) VALUES (2, 'user@user.com', true, 'user', 'user', 'user', 'user');


--
-- TOC entry 2289 (class 0 OID 0)
-- Dependencies: 193
-- Name: user_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('user_account_id_seq', 18, true);


--
-- TOC entry 2250 (class 0 OID 25108)
-- Dependencies: 195
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: root
--

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (5, 1);
INSERT INTO user_role (user_id, role_id) VALUES (4, 2);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO user_role (user_id, role_id) VALUES (5, 2);
INSERT INTO user_role (user_id, role_id) VALUES (18, 2);


--
-- TOC entry 2098 (class 2606 OID 25241)
-- Name: acl_class acl_class_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_class
    ADD CONSTRAINT acl_class_pkey PRIMARY KEY (id);


--
-- TOC entry 2106 (class 2606 OID 25276)
-- Name: acl_entry acl_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_entry
    ADD CONSTRAINT acl_entry_pkey PRIMARY KEY (id);


--
-- TOC entry 2102 (class 2606 OID 25251)
-- Name: acl_object_identity acl_object_identity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_object_identity
    ADD CONSTRAINT acl_object_identity_pkey PRIMARY KEY (id);


--
-- TOC entry 2094 (class 2606 OID 25231)
-- Name: acl_sid acl_sid_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_sid
    ADD CONSTRAINT acl_sid_pkey PRIMARY KEY (id);


--
-- TOC entry 2083 (class 2606 OID 25026)
-- Name: bill bill_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY bill
    ADD CONSTRAINT bill_pkey PRIMARY KEY (id);


--
-- TOC entry 2081 (class 2606 OID 25129)
-- Name: order_item order_item_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_pk PRIMARY KEY (product_id, order_id);


--
-- TOC entry 2085 (class 2606 OID 25131)
-- Name: order order_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_pk PRIMARY KEY (id);


--
-- TOC entry 2111 (class 2606 OID 25365)
-- Name: permission permission_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id);


--
-- TOC entry 2078 (class 2606 OID 16399)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 2087 (class 2606 OID 25052)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 2096 (class 2606 OID 25233)
-- Name: acl_sid unique_uk_1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_sid
    ADD CONSTRAINT unique_uk_1 UNIQUE (sid, principal);


--
-- TOC entry 2100 (class 2606 OID 25243)
-- Name: acl_class unique_uk_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_class
    ADD CONSTRAINT unique_uk_2 UNIQUE (class);


--
-- TOC entry 2104 (class 2606 OID 25253)
-- Name: acl_object_identity unique_uk_3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_object_identity
    ADD CONSTRAINT unique_uk_3 UNIQUE (object_id_class, object_id_identity);


--
-- TOC entry 2108 (class 2606 OID 25278)
-- Name: acl_entry unique_uk_4; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_entry
    ADD CONSTRAINT unique_uk_4 UNIQUE (acl_object_identity, ace_order);


--
-- TOC entry 2089 (class 2606 OID 25063)
-- Name: user user_account_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_account_pkey PRIMARY KEY (id);


--
-- TOC entry 2092 (class 2606 OID 25112)
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 2079 (class 1259 OID 24663)
-- Name: fki_product; Type: INDEX; Schema: public; Owner: root
--

CREATE INDEX fki_product ON order_item USING btree (product_id);


--
-- TOC entry 2109 (class 1259 OID 25366)
-- Name: permission_id_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX permission_id_uindex ON permission USING btree (id);


--
-- TOC entry 2090 (class 1259 OID 25354)
-- Name: user_username_uindex; Type: INDEX; Schema: public; Owner: root
--

CREATE UNIQUE INDEX user_username_uindex ON "user" USING btree (username);


--
-- TOC entry 2118 (class 2606 OID 25254)
-- Name: acl_object_identity foreign_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_object_identity
    ADD CONSTRAINT foreign_fk_1 FOREIGN KEY (parent_object) REFERENCES acl_object_identity(id);


--
-- TOC entry 2119 (class 2606 OID 25259)
-- Name: acl_object_identity foreign_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_object_identity
    ADD CONSTRAINT foreign_fk_2 FOREIGN KEY (object_id_class) REFERENCES acl_class(id);


--
-- TOC entry 2120 (class 2606 OID 25264)
-- Name: acl_object_identity foreign_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_object_identity
    ADD CONSTRAINT foreign_fk_3 FOREIGN KEY (owner_sid) REFERENCES acl_sid(id);


--
-- TOC entry 2121 (class 2606 OID 25279)
-- Name: acl_entry foreign_fk_4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_entry
    ADD CONSTRAINT foreign_fk_4 FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);


--
-- TOC entry 2122 (class 2606 OID 25284)
-- Name: acl_entry foreign_fk_5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acl_entry
    ADD CONSTRAINT foreign_fk_5 FOREIGN KEY (sid) REFERENCES acl_sid(id);


--
-- TOC entry 2114 (class 2606 OID 25137)
-- Name: order_item order_item_order_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_order_id_fk FOREIGN KEY (order_id) REFERENCES "order"(id);


--
-- TOC entry 2113 (class 2606 OID 25219)
-- Name: order_item order_item_product_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_product_id_fk FOREIGN KEY (product_id) REFERENCES product(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2115 (class 2606 OID 25154)
-- Name: order order_user_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2112 (class 2606 OID 25348)
-- Name: product product_user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_user_id_fk FOREIGN KEY (owner_id) REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2117 (class 2606 OID 25164)
-- Name: user_role user_role_role_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_role_fk FOREIGN KEY (role_id) REFERENCES role(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2116 (class 2606 OID 25159)
-- Name: user_role user_role_user_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2268 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: root
--

GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-03-22 14:35:23

--
-- PostgreSQL database dump complete
--

