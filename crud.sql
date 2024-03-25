--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    id uuid NOT NULL,
    title character varying(255),
    deleted boolean DEFAULT false,
    created_date timestamp without time zone,
    updated_date timestamp without time zone
);


ALTER TABLE public.category OWNER TO postgres;

--
-- Name: products; Type: TABLE; Schema: public; Owner: max
--

CREATE TABLE public.products (
    id uuid NOT NULL,
    title character varying(255) NOT NULL,
    article character varying(255) NOT NULL,
    description text,
    category_id uuid,
    price real,
    quantity integer DEFAULT 0 NOT NULL,
    created_date timestamp without time zone NOT NULL,
    updated_date timestamp without time zone NOT NULL,
    deleted boolean DEFAULT false
);


ALTER TABLE public.products OWNER TO max;

--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category (id, title, deleted, created_date, updated_date) FROM stdin;
704b98e1-a0fd-4f8a-8a4f-7d5ee0bd0197	zero	f	2024-03-24 18:36:19.172	2024-03-24 19:15:21.418
2b03b559-4eb1-43c5-9562-4535c7e7ded1	meat	f	2024-03-24 21:26:57.48	2024-03-24 21:26:57.48
74c96dc5-6b7c-4536-a54d-ff75f4a959b1	car	f	2024-03-24 23:38:46	2024-03-24 23:38:47
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: max
--

COPY public.products (id, title, article, description, category_id, price, quantity, created_date, updated_date, deleted) FROM stdin;
25182761-5a54-4f72-9c18-6e54aab84316	Test	125	Лучшие тесты оптом	704b98e1-a0fd-4f8a-8a4f-7d5ee0bd0197	567.64	246	2024-03-24 18:41:03.177	2024-03-24 18:41:03.177	f
7bc69b48-7782-453e-aa42-8d550861851f	Candy	123	Прощайте, зубы	2b03b559-4eb1-43c5-9562-4535c7e7ded1	125.64	256	2024-03-24 15:40:47.577	2024-03-24 15:40:47.577	f
8e79eec6-e94d-4edf-9230-4f295fb70764	Teeth	128a	Зубы на развес	\N	124	56256	2024-03-24 17:40:54.051	2024-03-24 17:40:54.051	f
ca93016a-1dc5-4793-9a3e-f2280ca57257	My	125a	Я и опт	2b03b559-4eb1-43c5-9562-4535c7e7ded1	1234.64	2	2024-03-24 17:43:05.307	2024-03-24 19:13:15.901	f
e9bc12fd-bd1f-4a4a-889e-22dca4354739	BWM	carmark	Супер пупер авто за небольшой бюджет	74c96dc5-6b7c-4536-a54d-ff75f4a959b1	345.64	56	2024-03-24 21:21:05.638	2024-03-24 21:21:05.638	f
d436ffeb-c94e-4207-b02b-6388e02f1898	Crash	124	Сомнительно, но ок-Эй	704b98e1-a0fd-4f8a-8a4f-7d5ee0bd0197	56	234	2024-03-23 22:47:57.711	2024-03-24 16:59:36.333	t
\.


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: products products_article_key; Type: CONSTRAINT; Schema: public; Owner: max
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_article_key UNIQUE (article);


--
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: max
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- Name: products fk1cf90etcu98x1e6n9aks3tel3; Type: FK CONSTRAINT; Schema: public; Owner: max
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fk1cf90etcu98x1e6n9aks3tel3 FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- PostgreSQL database dump complete
--

