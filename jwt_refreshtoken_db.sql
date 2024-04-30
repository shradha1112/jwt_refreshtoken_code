PGDMP     )                    |         
   securitydb    15.4    15.4                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            	           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            
           1262    123642 
   securitydb    DATABASE     }   CREATE DATABASE securitydb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_India.1252';
    DROP DATABASE securitydb;
                postgres    false            �            1259    123644    refreshtoken    TABLE     �   CREATE TABLE public.refreshtoken (
    id integer NOT NULL,
    expirydate timestamp(6) with time zone,
    token character varying(255),
    user_id integer
);
     DROP TABLE public.refreshtoken;
       public         heap    postgres    false            �            1259    123643    refreshtoken_id_seq    SEQUENCE     �   CREATE SEQUENCE public.refreshtoken_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.refreshtoken_id_seq;
       public          postgres    false    215                       0    0    refreshtoken_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.refreshtoken_id_seq OWNED BY public.refreshtoken.id;
          public          postgres    false    214            �            1259    123651    userinfo    TABLE     �   CREATE TABLE public.userinfo (
    id integer NOT NULL,
    email character varying(255),
    name character varying(255),
    password character varying(255),
    roles character varying(255)
);
    DROP TABLE public.userinfo;
       public         heap    postgres    false            �            1259    123650    userinfo_id_seq    SEQUENCE     �   CREATE SEQUENCE public.userinfo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.userinfo_id_seq;
       public          postgres    false    217                       0    0    userinfo_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.userinfo_id_seq OWNED BY public.userinfo.id;
          public          postgres    false    216            j           2604    123647    refreshtoken id    DEFAULT     r   ALTER TABLE ONLY public.refreshtoken ALTER COLUMN id SET DEFAULT nextval('public.refreshtoken_id_seq'::regclass);
 >   ALTER TABLE public.refreshtoken ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    215    215            k           2604    123654    userinfo id    DEFAULT     j   ALTER TABLE ONLY public.userinfo ALTER COLUMN id SET DEFAULT nextval('public.userinfo_id_seq'::regclass);
 :   ALTER TABLE public.userinfo ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    216    217                      0    123644    refreshtoken 
   TABLE DATA           F   COPY public.refreshtoken (id, expirydate, token, user_id) FROM stdin;
    public          postgres    false    215   �                 0    123651    userinfo 
   TABLE DATA           D   COPY public.userinfo (id, email, name, password, roles) FROM stdin;
    public          postgres    false    217                     0    0    refreshtoken_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.refreshtoken_id_seq', 37, true);
          public          postgres    false    214                       0    0    userinfo_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.userinfo_id_seq', 2, true);
          public          postgres    false    216            m           2606    123649    refreshtoken refreshtoken_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.refreshtoken
    ADD CONSTRAINT refreshtoken_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.refreshtoken DROP CONSTRAINT refreshtoken_pkey;
       public            postgres    false    215            o           2606    123660 )   refreshtoken uk_ktwna62rqu1wooo3c217o5oh0 
   CONSTRAINT     g   ALTER TABLE ONLY public.refreshtoken
    ADD CONSTRAINT uk_ktwna62rqu1wooo3c217o5oh0 UNIQUE (user_id);
 S   ALTER TABLE ONLY public.refreshtoken DROP CONSTRAINT uk_ktwna62rqu1wooo3c217o5oh0;
       public            postgres    false    215            q           2606    123658    userinfo userinfo_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.userinfo
    ADD CONSTRAINT userinfo_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.userinfo DROP CONSTRAINT userinfo_pkey;
       public            postgres    false    217            r           2606    123661 (   refreshtoken fkske6v5vk5hbvfmqr00ia66q16    FK CONSTRAINT     �   ALTER TABLE ONLY public.refreshtoken
    ADD CONSTRAINT fkske6v5vk5hbvfmqr00ia66q16 FOREIGN KEY (user_id) REFERENCES public.userinfo(id);
 R   ALTER TABLE ONLY public.refreshtoken DROP CONSTRAINT fkske6v5vk5hbvfmqr00ia66q16;
       public          postgres    false    217    3185    215               L   x�Ļ�  ��La�/�f�Aa���lBbH�R�`�E�ʓ|(����d��m���������M� ��0         �   x�U��
�0  ��|Ϧ�e�4#�5	b�Щ��N�>��~�O%n�1'�«%����!/�<��z���J�p-�je8�����g����n	L|1�.��8�>�@���f8��ɠ�5F�o���0*��;'�#M@��$56�U=�G�M���9j�і�p�F����q�=�@�     