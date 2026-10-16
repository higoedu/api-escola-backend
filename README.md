# 🏫 Sistema Escolar - API Back-end

Este repositório contém a API REST do Sistema Escolar, desenvolvida para gerenciar regras de negócio, persistência de dados e integrações da aplicação. O projeto foi construído utilizando boas práticas de desenvolvimento, testes automatizados e uma arquitetura em camadas altamente organizada.

---

### 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework Principal:** Spring Boot (Spring Web)
* **Persistência de Dados:** Spring Data JPA / Hibernate
* **Banco de Dados:** Microsoft SQL Server (Produção/Desenvolvimento) | H2 Database (Testes)
* **Gerenciador de Dependências:** Apache Maven
* **Testes Automatizados:** JUnit 5 & Mockito
* **Documentação Interativa:** Swagger / OpenAPI 3
* **Produtividade & Validação:** Lombok | Spring Boot Starter Validation (`@Valid`)
* **Utilitários:** Postman (Testes de endpoints)

---

### 📂 Estrutura de Pastas e Arquitetura

O projeto adota uma arquitetura em camadas bem definida, separando as responsabilidades de forma limpa:

* 📂 **`controller`** - Camada de entrada da aplicação, responsável por expor os endpoints REST e receber as requisições HTTP.
* 📂 **`dto`** (`RequestDTO`, `ResponseDTO`) - Objetos de transferência de dados usados para desacoplar as entidades de banco de dados da camada de visão, garantindo segurança e validação de payloads.
* 📂 **`service`** - Camada de negócio central, onde residem as regras, validações e fluxos principais do sistema escolar.
* 📂 **`repository`** - Camada de persistência que faz a interface direta com o banco de dados utilizando métodos e queries do Spring Data JPA.
* 📂 **`entity`** - Classes que mapeiam as tabelas relacionais do banco de dados (Alunos, Professores, etc.) utilizando anotações JPA.
* 📂 **`config`** (`CORS`, `OpenAPI`) - Classes de configuração global, responsável pela liberação de chamadas externas do Front-end e customização do Swagger.
* 📂 **`exception`** (`GlobalExceptionHandler`) - Centralizador de tratamento de erros baseado em `@ControllerAdvice`, interceptando exceções e devolvendo respostas HTTP padronizadas e limpas.
* 📂 **`enum`** - Tipos enumerados utilizados para padronizar dados estáticos e constantes do sistema com segurança.

---

### 🚀 Funcionalidades Principais

* **Operações CRUD Completas:** Gerenciamento otimizado de alunos e disciplinas com relacionamentos JPA.
* **Validação de Dados:** Uso rigoroso do Bean Validation para consistência nas entradas de dados (`@NotNull`, `@Size`, etc.).
* **Qualidade de Código:** Testes unitários isolados e mockados cobrindo Services e Controllers com JUnit 5 e Mockito.

---

### 🔧 Como Executar o Projeto (Passo a Passo)

#### **Pré-requisitos**
* Java JDK 17 ou superior instalado.
* Apache Maven instalado.
* Instância do Microsoft SQL Server ativa (ou configure para usar o H2 em memória).

#### **Instalação e Configuração**
1. Clone o repositório:
   ```bash
   git clone https://github.com
   ```
2. Acesse a pasta do projeto:
   ```bash
   cd api-escola-backend
   ```
3. Abra o arquivo `src/main/resources/application.properties` e altere as propriedades de conexão com as credenciais do seu SQL Server local:
   ```properties
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=escola_db;encrypt=true;trustServerCertificate=true;
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```
   *(Nota: Caso queira testar rapidamente sem configurar o SQL Server, você pode alterar o perfil do Spring para rodar o banco de dados H2 interno em memória).*

#### **Compilação e Execução**
1. Instale as dependências necessárias e compile o projeto através do Maven:
   ```bash
   mvn clean install
   ```
2. Execute a aplicação Spring Boot:
   ```bash
   mvn spring-boot:run
   ```
*A API inicializará o servidor embutido Tomcat e estará disponível por padrão em `http://localhost:8080`.*

#### **📄 Acessando a Documentação da API (Swagger)**
Com a aplicação rodando, você pode visualizar e testar todos os endpoints interativamente acessando a interface do Swagger no navegador:
`http://localhost:8080/swagger-ui/index.html`

---

### 📈 Conecte-se Comigo

* **LinkedIn:** [linkedin.com/in/higoeduardo](https://www.linkedin.com/in/higoeduardo/)
* **E-mail:** [higoedu@gmail.com]

---
💻 *Desenvolvido por Higo Eduardo como portfólio técnico Full Stack.*
