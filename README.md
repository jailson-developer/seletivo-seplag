# Controle de Servidor Público - Docker Compose

Este repositório contém a configuração necessária para rodar a aplicação de controle de servidor público utilizando Quarkus, com os serviços auxiliares de MinIO e PostgreSQL.

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

## ▶️ Passos para Rodar a Aplicação

### 1️⃣ Clonar o Repositório
```sh
git clone <URL_DO_REPOSITORIO>
cd <DIRETORIO_DO_REPOSITORIO>
```

### 2️⃣ Subir os Containers
```sh
docker-compose up -d
```

### 3️⃣ Verificar os Logs (Opcional)
```sh
docker-compose logs -f
```

### 4️⃣ Acessar o Swagger da API
Após subir os containers, acesse o Swagger pelo navegador:
```
http://localhost:8080/q/swagger-ui/
```

---

## ❌ Parar os Containers
Se precisar parar a aplicação e remover os containers:
```sh
docker-compose down
```

Se quiser remover volumes e redes associadas:
```sh
docker-compose down -v
```

---

## 🛠 Manutenção e Debugging

### 📌 Listar Containers em Execução
```sh
docker ps
```

### 📌 Acessar um Container
Para acessar o container do Quarkus:
```sh
docker exec -it <ID_DO_CONTAINER> /bin/sh
```

Para acessar o PostgreSQL:
```sh
docker exec -it <ID_DO_CONTAINER> psql -U postgres -d servidor_database
```

### 📌 Remover Imagens e Volumes (Se necessário)
```sh
docker system prune -a
docker volume prune
```