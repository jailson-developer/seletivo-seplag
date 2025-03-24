create extension unaccent;

CREATE TABLE public.cidade
(
    cid_id   bigserial    NOT NULL,
    cid_nome varchar(200) NULL,
    cid_uf   varchar(2)   NULL,
    CONSTRAINT cidade_pkey PRIMARY KEY (cid_id)
);

CREATE TABLE public.endereco
(
    end_id              bigserial    NOT NULL,
    end_tipo_logradouro varchar(50)  NULL,
    end_logradouro      varchar(200) NULL,
    end_numero          varchar(10)  NULL,
    end_bairro          varchar(100) NULL,
    cid_id              int8         NULL,
    CONSTRAINT endereco_pkey PRIMARY KEY (end_id),
    CONSTRAINT fk_cidade FOREIGN KEY (cid_id) REFERENCES public.cidade (cid_id)
);

CREATE TABLE public.unidade
(
    unid_id    bigserial    NOT NULL,
    unid_nome  varchar(200) NULL,
    unid_sigla varchar(20)  NULL,
    CONSTRAINT unidade_pkey PRIMARY KEY (unid_id)
);

CREATE TABLE public.unidade_endereco
(
    unid_id int8 NOT NULL,
    end_id  int8 NOT NULL,
    CONSTRAINT unidade_endereco_pkey PRIMARY KEY (unid_id, end_id),
    CONSTRAINT fk_unidade_endereco_endereco FOREIGN KEY (end_id) REFERENCES public.endereco (end_id),
    CONSTRAINT fk_unidade_endereco_unidade FOREIGN KEY (unid_id) REFERENCES public.unidade (unid_id)
);


CREATE TABLE public.pessoa
(
    pes_id              bigserial    NOT NULL,
    pes_nome            varchar(200) NULL,
    pes_data_nascimento date         NULL,
    pes_sexo            varchar(9)   NULL,
    pes_mae             varchar(200) NULL,
    pes_pai             varchar(200) NULL,
    CONSTRAINT pessoa_pkey PRIMARY KEY (pes_id)
);

CREATE TABLE public.pessoa_endereco
(
    pes_id int8 NOT NULL,
    end_id int8 NOT NULL,
    CONSTRAINT pessoa_endereco_pkey PRIMARY KEY (pes_id, end_id),
    CONSTRAINT fk_pessoa_endereco_endereco FOREIGN KEY (end_id) REFERENCES public.endereco (end_id),
    CONSTRAINT fk_pessoa_endereco_pessoa FOREIGN KEY (pes_id) REFERENCES public.pessoa (pes_id)
);

CREATE TABLE public.foto_pessoa
(
    fp_id     bigserial    NOT NULL,
    pes_id    int8         NOT NULL,
    fp_data   date         NULL,
    fp_bucket varchar(50)  NULL,
    fp_hash   varchar(200) NULL,
    CONSTRAINT foto_pessoa_pk PRIMARY KEY (fp_id, pes_id),
    CONSTRAINT fk_foto_pessoa_pessoa FOREIGN KEY (pes_id) REFERENCES public.pessoa (pes_id)
);

CREATE TABLE public.lotacao
(
    lot_id           bigserial    NOT NULL,
    pes_id           int8         NULL,
    unid_id          int8         NULL,
    lot_data_lotacao date         NULL,
    lot_data_remocao date         NULL,
    lot_portaria     varchar(100) NULL,
    CONSTRAINT lotacao_pkey PRIMARY KEY (lot_id),
    CONSTRAINT fk_lotacao_pessoa FOREIGN KEY (pes_id) REFERENCES public.pessoa (pes_id),
    CONSTRAINT fk_unidade FOREIGN KEY (unid_id) REFERENCES public.unidade (unid_id)
);

CREATE TABLE public.servidor_efetivo
(
    pes_id       int8        NOT NULL,
    se_matricula varchar(20) NULL,
    CONSTRAINT servidor_efetivo_pkey PRIMARY KEY (pes_id),
    CONSTRAINT fk_servidor_efetivo_pessoa FOREIGN KEY (pes_id) REFERENCES public.pessoa (pes_id)
);

CREATE TABLE public.servidor_temporario
(
    pes_id           int8 NOT NULL,
    st_data_admissao date NULL,
    st_data_demissao date NULL,
    CONSTRAINT servidor_temporario_pkey PRIMARY KEY (pes_id),
    CONSTRAINT fk_servidor_pessoa FOREIGN KEY (pes_id) REFERENCES public.pessoa (pes_id)
);

CREATE TABLE public."role"
(
    role_id        bigserial    NOT NULL,
    role_nome      varchar(40)  NULL,
    role_descricao varchar(100) NULL,
    CONSTRAINT role_pkey PRIMARY KEY (role_id)
);


CREATE TABLE public.usuario
(
    user_id       bigserial    NOT NULL,
    user_name     varchar(80)  NULL,
    user_login    varchar(40)  NULL,
    user_password varchar(250) NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (user_id)
);

CREATE TABLE public.role_user
(
    role_id int8 NOT NULL,
    user_id int8 NOT NULL,
    CONSTRAINT role_user_pkey PRIMARY KEY (role_id, user_id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES public."role" (role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.usuario (user_id)
);
