INSERT INTO public.usuario (user_name,user_login,user_password) VALUES
    ('Admin','admin','$2a$12$DOTgDxZ8Y4frK7I/eVH/MOCA.HLsnoT/kE3sl8/S83jMMUiJDnJgy');

INSERT INTO public."role" (role_nome,role_descricao) VALUES
    ('manter_servidor','Permite criar, editar e excluir servidores'),
    ('leitura_servidor','Permite apenas leitura de servidores'),
    ('manter_lotacao','Permite criar, editar e excluir lotações'),
    ('leitura_lotacao','Permite apenas leitura de lotações'),
    ('manter_unidade','Permite criar, editar e excluir unidades'),
    ('leitura_unidade','Permite apenas leitura de unidades');

INSERT INTO public.role_user (role_id,user_id) VALUES
    ((SELECT role_id FROM public."role" WHERE role_nome = 'manter_servidor'),(SELECT user_id FROM public.usuario WHERE user_name = 'Admin')),
    ((SELECT role_id FROM public."role" WHERE role_nome = 'leitura_servidor'),(SELECT user_id FROM public.usuario WHERE user_name = 'Admin')),
    ((SELECT role_id FROM public."role" WHERE role_nome = 'manter_lotacao'),(SELECT user_id FROM public.usuario WHERE user_name = 'Admin')),
    ((SELECT role_id FROM public."role" WHERE role_nome = 'leitura_lotacao'),(SELECT user_id FROM public.usuario WHERE user_name = 'Admin')),
    ((SELECT role_id FROM public."role" WHERE role_nome = 'manter_unidade'),(SELECT user_id FROM public.usuario WHERE user_name = 'Admin')),
    ((SELECT role_id FROM public."role" WHERE role_nome = 'leitura_unidade'),(SELECT user_id FROM public.usuario WHERE user_name = 'Admin'));
