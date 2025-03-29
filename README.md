# Controle de Servidor Público - Docker Compose

Este repositório contém a configuração necessária para rodar a aplicação de controle de servidor público utilizando Quarkus, com os serviços auxiliares de MinIO e PostgreSQL.

## Dados da Inscrição

  - Nome: `Jailson Sales Ribeiro`
  - Telefone: `(66) 99975-1536`
  - Email: `jailsonsalestxu@hotmail.com`
  - Vaga: `Desenvolvedor Backend Sênior`

## 📌 Pré-requisitos

Antes de rodar os containers, verifique se você possui as seguintes ferramentas instaladas no seu sistema:

- **Docker**: [Instalar Docker](https://www.docker.com/get-started)
- **Docker Compose**: [Instalar Docker Compose](https://docs.docker.com/compose/install/)

## 📂 Estrutura do Docker Compose

O arquivo `docker-compose.yml` define três serviços principais:

1. **MinIO** - Armazenamento de objetos, configurado com credenciais padrão.
2. **PostgreSQL** - Banco de dados PostgreSQL com inicialização via script SQL.
3. **Servidor API (Quarkus)** - API para controle de servidores, que depende do PostgreSQL e MinIO.

---

## ⚙️ Serviços Definidos

### 🗂 MinIO (Armazenamento de Objetos)
- **Imagem**: `quay.io/minio/minio:latest`
- **Porta**: `9000`
- **Credenciais**:
    - Usuário: `minioadmin`
    - Senha: `minioadmin`
- **Volume**: `minio_data`
- **Comando de Inicialização**:
  ```sh
  server --address 0.0.0.0:9000 /data
  ```
- **Modo de Rede**: `host`

---

### 🛢 PostgreSQL (Banco de Dados)
- **Imagem**: `postgres:latest`
- **Porta**: `5432`
- **Credenciais**:
    - Usuário: `postgres`
    - Senha: `root`
    - Banco de dados: `servidor_database`
- **Script de Inicialização**: `init.sql`
- **Modo de Rede**: `host`

---

### 🚀 Servidor API (Quarkus)
- **Imagem**: `quarkus/controle-servidor-api-jvm:latest`
- **Porta**: `8080`
- **Dependências**:
    - PostgreSQL (`servidor_database`)
    - MinIO (armazenamento de objetos)
- **Variáveis de Ambiente**:
  ```env
  JDBC_URL=jdbc:postgresql://localhost:5432/servidor_database
  JDBC_USERNAME=postgres
  JDBC_PASSWORD=root
  MINIO_ACCESS_KEY=minioadmin
  MINIO_SECRET_KEY=minioadmin
  MINIO_HOST=localhost
  MINIO_PORT=9000
  MINIO_EXPIRACAO=5M
  CORS_ORIGINS=http://localhost:8080
  ```
- **Modo de Rede**: `host`

---


# ▶️ Passos para Rodar a Aplicação

## 1️⃣ Clonar o Repositório

```sh
git clone <URL_DO_REPOSITORIO>
cd <DIRETORIO_DO_REPOSITORIO>
```

## 2️⃣ Subir os Containers

```sh
docker-compose up
```
- **Nota**: Para rodar em segundo plano, use `docker-compose up -d`.

## 3️⃣ Acessar o Swagger UI

- Com os containers em execução, abra o navegador e acesse a interface Swagger para explorar e testar a API:
  - [Swagger UI](http://localhost:8080/q/swagger-ui/)
- **Credenciais Padrão**:
  - **Usuário**: `admin`
  - **Senha**: `1`
- **Dica**: Caso a interface não carregue, verifique se o serviço Quarkus subiu corretamente com `docker-compose logs`.

## ▶️ Testando a Aplicação

1. No Swagger UI, autentique-se com as credenciais fornecidas (`admin/1`).
2. Explore os endpoints disponíveis para gerenciar servidores públicos.
3. Utilize o MinIO em [http://localhost:9000](http://localhost:9000) (login: `minioadmin` / senha: `minioadmin`) para visualizar ou gerenciar arquivos, se necessário.

## ⏹️ Parar a Aplicação

- Para encerrar os serviços, use:
  ```sh
  docker-compose down
  ```
- Para remover os volumes (dados persistentes), adicione a flag `--volumes`:
  ```sh
  docker-compose down --volumes
  ```

## 📋 Notas Adicionais

- Certifique-se de que as portas `8080`, `9000` e `5432` estejam livres antes de subir os containers.
- O script `init.sql` inicializa o banco de dados automaticamente ao subir o PostgreSQL.
- Em caso de erros, consulte os logs com:
  ```sh
  docker-compose logs <nome_do_serviço>
  ```
  Exemplo: `docker-compose logs quarkus`, `docker-compose logs minio`, `docker-compose logs postgres`.