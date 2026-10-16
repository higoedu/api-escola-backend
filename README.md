# 🏫 Sistema Escolar - API Back-end

Este repositório contém a API REST do Sistema Escolar, desenvolvida para gerenciar regras de negócio, persistência de dados e integrações da aplicação. O projeto foi construído utilizando boas práticas de desenvolvimento, arquitetura em camadas e soluções robustas para o ecossistema corporativo.

---

### 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework Principal:** Spring Boot (Spring Web)
* **Persistência de Dados:** Spring Data JPA / Hibernate
* **Banco de Dados:** Microsoft SQL Server (Produção/Desenvolvimento) | H2 Database (Testes)
* **Gerenciador de Dependências:** Apache Maven
* **Testes Automatizados:** JUnit 5 & Mockito
* **Validações e Utilitários:** Postman (Testes de integração de endpoints)

---

### 🚀 Funcionalidades Principais

* **Operações CRUD Completas:** Gerenciamento de alunos, professores, turmas e notas.
* **Regras de Negócio Implementadas:** Validação de matrículas, cálculo automático de médias e restrições de horários.
* **Persistência Robusta:** Consultas otimizadas utilizando Spring Data JPA sobre banco relacional.
* **Qualidade de Código:** Testes unitários cobrindo Services e Controllers com JUnit e Mockito.
* **Segurança de Integração:** Configuração de políticas de CORS integrada para permitir a comunicação segura com o ecossistema Front-end.

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

---

### 📈 Conecte-se Comigo

* **LinkedIn:** [linkedin.com/in/higoeduardo](https://www.linkedin.com/in/higoeduardo/)
* **E-mail:** [higoedu@gmail.com]

---
💻 *Desenvolvido por Higo Eduardo como portfólio técnico Full Stack.*
