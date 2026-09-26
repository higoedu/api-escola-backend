package br.com.higo.apiescola.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlunoRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void configurarValidator() {
        validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
    }

    @Test
    void deveRetornarMensagemAoInformarSemestreTres() {

        AlunoRequestDTO dto = new AlunoRequestDTO();

        dto.setSemestreIngresso(3);

        var erros = validator.validateProperty(
                dto,
                "semestreIngresso"
        );

        assertFalse(erros.isEmpty());

        assertTrue(
                erros.stream()
                        .anyMatch(erro ->
                                erro.getMessage().contains(
                                        "Semestre de ingresso do aluno deve ser 1 ou 2!"
                                )
                        )
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void deveAceitarSemestresValidos(int semestre) {

        AlunoRequestDTO dto = new AlunoRequestDTO();

        dto.setSemestreIngresso(semestre);

        var erros = validator.validateProperty(
                dto,
                "semestreIngresso"
        );

        assertTrue(erros.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3})
    void deveRejeitarSemestresInvalidos(int semestre) {

        AlunoRequestDTO dto = new AlunoRequestDTO();

        dto.setSemestreIngresso(semestre);

        var erros = validator.validateProperty(
                dto,
                "semestreIngresso"
        );

        assertFalse(erros.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"João", "Maria", "Higo"})
    void deveAceitarNomesValidos(String nome) {

        AlunoRequestDTO dto = new AlunoRequestDTO();

        dto.setAluno(nome);

        var erros = validator.validateProperty(
                dto,
                "aluno"
        );

        assertTrue(erros.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void deveRejeitarNomesInvalidos(String nome) {

        AlunoRequestDTO dto = new AlunoRequestDTO();

        dto.setAluno(nome);

        var erros = validator.validateProperty(
                dto,
                "aluno"
        );

        assertFalse(erros.isEmpty());
    }

    @Test
    void deveRetornarMensagemAoInformarNomeVazio() {

        AlunoRequestDTO dto = new AlunoRequestDTO();

        dto.setAluno("");

        var erros = validator.validateProperty(
                dto,
                "aluno"
        );

        assertFalse(erros.isEmpty());

        assertTrue(
                erros.stream()
                        .anyMatch(erro ->
                                erro.getMessage().contains(
                                        "Nome do aluno é obrigatório!"
                                )
                        )
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2025, 2026})
    void deveAceitarAnosDeIngressoValidos(int anoIngresso) {

        AlunoRequestDTO dto = new AlunoRequestDTO();

        dto.setAnoIngresso(anoIngresso);

        var erros = validator.validateProperty(
                dto,
                "anoIngresso"
        );

        assertTrue(erros.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void deveRejeitarAnosDeIngressoInvalidos(int anoIngresso) {

        AlunoRequestDTO dto = new AlunoRequestDTO();

        dto.setAnoIngresso(anoIngresso);

        var erros = validator.validateProperty(
                dto,
                "anoIngresso"
        );

        assertFalse(erros.isEmpty());
    }

    @Test
    void deveRejeitarAnoDeIngressoNulo() {

        AlunoRequestDTO dto = new AlunoRequestDTO();

        dto.setAnoIngresso(null);

        var erros = validator.validateProperty(
                dto,
                "anoIngresso"
        );

        assertFalse(erros.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ATIVO", "INATIVO", "CONCLUIDO"})
    void deveAceitarSituacoesValidas(String situacao) {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setSituacaoAluno(situacao);

        var erros = validator.validateProperty(
                dto,
                "situacaoAluno"
        );

        assertTrue(erros.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void deveRejeitarSituacoesInvalidas(String situacao) {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setSituacaoAluno(situacao);

        var erros = validator.validateProperty(
                dto,
                "situacaoAluno"
        );

        assertFalse(erros.isEmpty());
    }

    @Test
    void deveRetornarMensagemAoInformarSituacaoVazio() {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setSituacaoAluno("");

        var erros = validator.validateProperty(
                dto,
                "situacaoAluno"
        );

        assertFalse(erros.isEmpty());

        assertTrue(
                erros.stream()
                        .anyMatch(erro ->
                                erro.getMessage().contains(
                                        "Situação do aluno é obrigatório!"
                                )
                        )
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 10L, 100L})
    void deveAceitarDisciplinasValidas(Long disciplinaId) {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setDisciplinaId(disciplinaId);

        var erros = validator.validateProperty(
                dto,
                "disciplinaId"
        );

        assertTrue(erros.isEmpty());
    }

    @Test
    void deveRejeitarDisciplinaNula() {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setDisciplinaId(null);

        var erros = validator.validateProperty(
                dto,
                "disciplinaId"
        );

        assertFalse(erros.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Sistemas de Informação", "Engenharia de Software", "Administração"})
    void deveAceitarCursosValidos(String curso) {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setCurso(curso);

        var erros = validator.validateProperty(
                dto,
                "curso"
        );

        assertTrue(erros.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void deveRejeitarCursosInvalidos(String curso) {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setCurso(curso);

        var erros = validator.validateProperty(
                dto,
                "curso"
        );

        assertFalse(erros.isEmpty());
    }

    @Test
    void deveRetornarMensagemAoInformarCursoVazio() {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setCurso("");

        var erros = validator.validateProperty(
                dto,
                "curso"
        );

        assertFalse(erros.isEmpty());

        assertTrue(
                erros.stream()
                        .anyMatch(erro ->
                                erro.getMessage().contains(
                                        "Nome do curso é obrigatório!"
                                )
                        )
        );
    }

    @Test
    void deveRejeitarSemestreDeIngressoNulo() {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setSemestreIngresso(null);

        var erros = validator.validateProperty(
                dto,
                "semestreIngresso"
        );

        assertFalse(erros.isEmpty());
    }
}
