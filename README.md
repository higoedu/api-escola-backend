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

#### 💡 Exemplo Prático: Mapeamento, Validação e Teste de Endpoint (Postman)

Abaixo está demonstrada a arquitetura ponta a ponta que valida e processa o cadastro de um Aluno vinculado a uma Disciplina existente no banco de dados:

<details>
<summary>📂 1. Camada de Entrada e Validação (Controller & DTO)</summary>

O `Controller` intercepta a requisição e ativa o Bean Validation com a anotação `@Valid`. O `AlunoRequestDTO` restringe as entradas e define mensagens de erro customizadas interceptadas pelo `GlobalExceptionHandler`:

```java
// No AlunoController.java
@PostMapping
public ResponseEntity<AlunoResponseDTO> salvar (@Valid @RequestBody AlunoRequestDTO request) {
    AlunoResponseDTO salvo = alunoService.salvar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
}

// No AlunoRequestDTO.java
@Data
public class AlunoRequestDTO {
    @NotBlank(message = "Nome do aluno é obrigatório!")
    private String aluno;

    @NotNull(message = "Ano de ingresso do aluno é obrigatório!")
    @Min(value = 1, message = "Ano de ingresso do aluno deve ser maior que zero!")
    private Integer anoIngresso;

    @NotNull(message = "Semestre de ingresso do aluno é obrigatório!")
    @Min(value = 1, message = "Semestre de ingresso do aluno deve ser 1 ou 2!")
    @Max(value = 2, message = "Semestre de ingresso do aluno deve ser 1 ou 2!")
    private Integer semestreIngresso;

    @NotBlank(message = "Situação do aluno é obrigatório!")
    private String situacaoAluno;

    @NotNull(message = "disciplinaId é obrigatória!")
    private Long disciplinaId;

    @NotBlank(message = "Nome do curso é obrigatório!")
    private String curso;
}
```
</details>

<details>
<summary>📂 2. Camada de Regras de Negócio (Service)</summary>

O `Service` recebe o DTO validado, efetua a busca relacional da `Disciplina` por ID lançando uma exceção tratada se não encontrada, e mapeia o resultado para o DTO de resposta:

```java
public AlunoResponseDTO salvar(AlunoRequestDTO request) {
    Aluno aluno = new Aluno();

    aluno.setAluno(request.getAluno());
    aluno.setAnoIngresso(request.getAnoIngresso());
    aluno.setSemestreIngresso(request.getSemestreIngresso());
    aluno.setSituacaoAluno(request.getSituacaoAluno());
    aluno.setDataIngresso(new Date());

    Disciplina disciplina = disciplinaRepository
            .findById(request.getDisciplinaId())
            .orElseThrow(() -> new RuntimeException("Disciplina não encontrada!"));

    aluno.setDisciplina(disciplina);
    aluno.setCurso(request.getCurso());

    aluno = alunoRepository.save(aluno);

    return toResponseDTO(aluno);
}
```
</details>

<details>
<summary>🚀 3. Teste de Endpoint no Postman</summary>

**Estrutura do Payload de Entrada (`POST /alunos`)**
```json
{
    "aluno": "João da Silva",
    "anoIngresso": 2026,
    "semestreIngresso": 1,
    "situacaoAluno": "Ativo",
    "disciplinaId": 2,
    "curso": "Engenharia de Computação"
}
```
*Se as regras de validação forem violadas (ex: `semestreIngresso = 0`), a API responderá de forma padronizada através do `GlobalExceptionHandler` explicitando os erros capturados.*
</details>

---

<details>
<summary>🧪 4. Cobertura de Testes Automatizados (JUnit 5 & Mockito)</summary>

Abaixo estão exemplificados os quatro cenários de testes automatizados implementados para validar as camadas de Controller, Service e DTO:

#### **A. Teste de Integração WebMvc (Controller com Contexto HTTP)**
Valida a rota HTTP, o comportamento do status `201 CREATED` e a correta interceptação do payload JSON pelo Spring:
```java
@Autowired
private MockMvc mockMvc;

@MockitoBean
private AlunoService alunoService;

@Test
void deveSalvarAlunoComDadosValidos() throws Exception {
    String json = """
        {
            "aluno": "João da Silva",
            "anoIngresso": 2026,
            "curso": "Engenharia de Computação",
            "semestreIngresso": 1,
            "situacaoAluno": "ATIVO",
            "disciplinaId": 1
        }
        """;

    AlunoResponseDTO alunoSalvo = new AlunoResponseDTO();

    when(alunoService.salvar(any(AlunoRequestDTO.class))).thenReturn(alunoSalvo);

    mockMvc.perform(
                    post("/alunos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
            )
            .andExpect(status().isCreated());

    verify(alunoService).salvar(any(AlunoRequestDTO.class));
}
```

#### **B. Teste Unitário Isolado do Controller**
Valida o retorno do método do Controller garantindo o contrato e o empacotamento das respostas HTTP:
```java
@Mock
private AlunoService alunoService;

@InjectMocks
private AlunoController alunoController;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void deveSalvarAluno() {
    AlunoRequestDTO request = new AlunoRequestDTO();
    AlunoResponseDTO salvo = new AlunoResponseDTO();

    when(alunoService.salvar(request)).thenReturn(salvo);

    ResponseEntity<AlunoResponseDTO> response = alunoController.salvar(request);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(salvo, response.getBody());

    verify(alunoService).salvar(request);
}
```

#### **C. Teste Unitário de Validação do DTO (Jakarta Validation)**
Isola e força a engine do Jakarta Bean Validation a validar as anotações do DTO (como limites de `@Min` e `@Max`):
```java
private Validator validator;

@BeforeEach
void configurarValidator() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
}

@Test
void deveRetornarMensagemAoInformarSemestreTres() {
    AlunoRequestDTO dto = new AlunoRequestDTO();
    dto.setSemestreIngresso(3); // Viola o teto máximo @Max(2)

    var erros = validator.validateProperty(dto, "semestreIngresso");

    assertFalse(erros.isEmpty());
    assertTrue(erros.stream().anyMatch(erro -> 
        erro.getMessage().contains("Semestre de ingresso do aluno deve ser 1 ou 2!")
    ));
}
```

#### **D. Teste Unitário Completo do Service (Regras de Negócio & Relacionamento)**
Garante o fluxo ideal do negócio, mockando os repositórios JPA de `Aluno` e `Disciplina` para testar o mapeamento e amarração das chaves estrangeiras:
```java
@Mock
private AlunoRepository alunoRepository;

@Mock
private DisciplinaRepository disciplinaRepository;

private AlunoService alunoService;

@BeforeEach
void setUp() {
    alunoService = new AlunoService(alunoRepository, disciplinaRepository);
}

@Test
void deveSalvarAlunoComSucesso() {
    AlunoRequestDTO request = new AlunoRequestDTO();
    request.setAluno("João da Silva");
    request.setAnoIngresso(2026);
    request.setSemestreIngresso(1);
    request.setSituacaoAluno("ATIVO");
    request.setDisciplinaId(1L);
    request.setCurso("Sistemas de Informação");

    Disciplina disciplina = new Disciplina();
    disciplina.setId(1L);

    Aluno alunoSalvo = new Aluno();
    alunoSalvo.setId(10L);
    alunoSalvo.setAluno("João da Silva");
    alunoSalvo.setAnoIngresso(2026);
    alunoSalvo.setSemestreIngresso(1);
    alunoSalvo.setSituacaoAluno("ATIVO");
    alunoSalvo.setDisciplina(disciplina);
    alunoSalvo.setCurso("Sistemas de Informação");

    when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
    when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoSalvo);

    AlunoResponseDTO response = alunoService.salvar(request);

    assertNotNull(response);
    assertEquals(10L, response.getId());
    assertEquals("João da Silva", response.getAluno());
    assertEquals(1L, response.getDisciplinaId());

    verify(disciplinaRepository).findById(1L);
    verify(alunoRepository).save(any(Aluno.class));
}
```
</details>

### 📈 Conecte-se Comigo

* **LinkedIn:** [linkedin.com/in/higoeduardo](https://www.linkedin.com/in/higoeduardo/)
* **E-mail:** [higoedu@gmail.com]

---
💻 *Desenvolvido por Higo Eduardo como portfólio técnico Full Stack.*
