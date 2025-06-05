# 🐾 MundoPet - API para Blog de Pets

## 📝 Descrição

O **MundoPet** é uma **API REST** desenvolvida para gerenciar um blog de conteúdo sobre pets. Ela permite que aplicações cliente criem, visualizem, editem e excluam posts sobre animais de estimação. Essa API serve como backend para plataformas voltadas a amantes de animais, facilitando o compartilhamento de conhecimentos sobre cuidados, alimentação, comportamento e curiosidades sobre pets.

---

## 🚀 Tecnologias Utilizadas
- **Spring Boot 3.4.4**: Framework Java para desenvolvimento de aplicações
- **Spring Data JPA**: Para persistência de dados
- **PostgreSQL**: Banco de dados relacional
- **H2 Database**: Banco de dados em memória para testes
- **Flyway**: Controle de versão para banco de dados
- **Swagger/OpenAPI 3**: Documentação automática da API
- **MapStruct**: Para mapeamento de objetos
- **Lombok**: Redução de código boilerplate
- **Docker e Docker Compose**: Containerização da aplicação

## 🧪 Testes
- JUnit 5
- Mockito
- Spring Boot Test
- MockMvc (para testes com endpoints REST)

## 🛠️ Requisitos
- Java 21
- Maven 3.9+
- Docker e Docker Compose 

## 🌐 Endpoints
A API estará disponível em `http://localhost:8080`

| Método | URL                   | Descrição               |
|--------|------------------------|--------------------------|
| POST   | /api/v1/posts          | Criar um novo post       |
| GET    | /api/v1/posts          | Listar todos os posts (paginado) |
| GET    | /api/v1/posts/{id}     | Buscar post por ID       |
| PUT    | /api/v1/posts/{id}     | Atualizar post existente |
| DELETE | /api/v1/posts/{id}     | Excluir post             |

## 📂 Estrutura dos Endpoints

### Criar um post
- **POST** `http://localhost:8080/api/v1/posts` 
```json
{
  "titulo": "Como preparar sua casa para um novo pet",
  "descricao": "Vai receber um novo amigo peludo? Veja dicas fáceis para deixar sua casa segura e acolhedora para o seu pet desde o primeiro dia!",
  "autor": "Julia Silva"
}
```
### Buscar Post por ID
**GET** `http://localhost:8080/api/v1/posts/{id}` 
### Listar Post (com paginação e ordenação)
**GET** `http://localhost:8080/api/v1/posts`
###  Atualizar um Post
**PUT**  `http://localhost:8080/api/v1/posts/{id}` 
```json
{
  "titulo": "Pet envelhecendo? Veja como adaptar os cuidados com a idade",
  "descricao": "Conselhos práticos para cuidar de cães e gatos idosos, incluindo alimentação especial, visitas ao veterinário e conforto no dia a dia!",
  "autor": "Julia Silva"
}
```
### Excluir um Post
- **DELETE** `http://localhost:8080/api/v1/posts/{id}` 

---

### Parâmetros de Paginação e Ordenação
- `page`: Número da página (começa em 0)
- `size`: Quantidade de itens por página
- `sort`: Campo para ordenação (ex: titulo, autor, dataCriacao)

**Exemplo**:  
`GET /api/v1/posts?page=0&size=10&sort=titulo`

### Parâmetros de Paginação e Ordenação
- `page`: Número da página (começa em 0)
- `size`: Quantidade de itens por página
- `sort`: Campo para ordenação (ex: titulo, autor, dataCriacao)

**Exemplo**:  
`GET /api/v1/posts?page=0&size=10&sort=titulo`

## 📊 Estrutura do Projeto

```
src
├── main
│   ├── java
│   │   └── com.sylviavitoria.blogpets
|   |       ├──config
│   │       ├── controller
│   │       ├── dto
│   │       ├── exception
│   │       ├── interfaces
│   │       ├── mapper
│   │       ├── model
│   │       ├── repository
│   │       ├── service
│   │       └── BlogpetsApplication.java
│   └── resources
│       ├── db/migration
│       │   └── V1__criar_tabela_posts.sql
│       ├── application.properties
│       ├── application-h2.properties
│       └── application-postgres.properties
├── test
│   └── java
│       └── com.sylviavitoria.blogpets
│           ├── controller
│           ├── exception
│           ├── model
│           ├── service
│           └── BlogpetsApplicationTests.java
```

## 📝 Modelo de Dados

### Post
- `id`: Long (auto-incremental)
- `titulo`: String (min: 5, max: 100 caracteres)
- `descricao`: Text (min: 10 caracteres)
- `autor`: String (min: 3 caracteres)
- `dataCriacao`: DateTime (auto-preenchido)

## 🔄 Perfis de Execução
A aplicação contém dois perfis configurados:
- `h2`: Utiliza banco de dados em memória (padrão para desenvolvimento)
- `postgres`: Utiliza PostgreSQL (recomendado para produção)

Para alterar o perfil, modifique a propriedade `spring.profiles.active` no arquivo `application.properties` ou defina a variável de ambiente `SPRING_PROFILES_ACTIVE`.


### 👁️ Configuração `.env`

```bash
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=apiblogpets
POSTGRES_PORT=5432

SPRING_PROFILES_ACTIVE=postgres
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/apiblogpets
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres

PGADMIN_PORT=5050
PGADMIN_DEFAULT_EMAIL=admin@admin.com
PGADMIN_DEFAULT_PASSWORD=admin
```

> Esse arquivo `.env` é utilizado para centralizar as variáveis de ambiente da aplicação, deixando sua configuração mais simples e segura, especialmente quando for rodar via Docker ou em ambientes diferentes.

🐳 **Observação**:
- O Docker Compose utiliza essas variáveis para subir os containers do PostgreSQL e PgAdmin com as credenciais corretas.
- O Spring Boot também lerá essas variáveis ao inicializar, garantindo que a conexão com o banco seja automática conforme o ambiente.

---

# 🌬️ Como Executar

## Pré-requisitos
- Java 21+
- Maven
- Docker
- PostgreSQL ou H2 Database

## Passos para Execução

### 1. Clone o repositório
```bash
git clone https://github.com/sylviavitoria/back-blog-pets.git
cd back-blog-pets
```

### 2. Configure o ambiente
Ajuste as configurações de banco de dados em um dos arquivos:
- Configure suas credencias citado anteriormente e defina variáveis no arquivo `.env`

### 3. Execute a aplicação

**Opção 1: Via Maven**
```bash
mvn spring-boot:run
```

**Opção 2: Via Docker**
```bash
docker compose up --build
```

## 4. Acesse a Aplicação

### Documentação da API
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`

### Teste dos Endpoints
- **Postman:** Adicione os Endpoints da API mencionados anteriormente `http://localhost:8080/api/v1/...`

### Gerenciamento do Banco de Dados
- **H2 Database** (para ambiente de desenvolvimento):  
  - Console: `http://localhost:8080/h2-console`

- **PostgreSQL** (recomendado para ambiente Docker):  
  - ### Para acessar o PgAdmin:
  - Credenciais de login: Definidas no arquivo `.env`
  ###Exemplo:
  - **URL:** http://localhost:5050  
  - **Email:** admin@admin.com  
  - **Senha:** admin

 # 🖼️ Interface Front-end

O **MundoPet** possui uma interface web desenvolvida com **React** com **TypeScript**, que se comunica com esta API REST para oferecer uma experiência fluida e interativa aos usuários. O front permite:

- Visualizar todos os posts
- Criar novos conteúdos
- Editar posts existentes
- Excluir posts indesejados

### 📁 Repositório Front-end

Você pode acessar o repositório do front-end neste link: [Blog-pets](https://github.com/sylviavitoria/Blog-pets)
