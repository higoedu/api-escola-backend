package br.com.higo.apiescola.service;

import br.com.higo.apiescola.dto.AlunoRequestDTO;
import br.com.higo.apiescola.dto.AlunoResponseDTO;
import br.com.higo.apiescola.entity.Aluno;
import br.com.higo.apiescola.entity.Disciplina;
import br.com.higo.apiescola.repository.AlunoRepository;
import br.com.higo.apiescola.repository.DisciplinaRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

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

        when(disciplinaRepository.findById(1L))
                .thenReturn(Optional.of(disciplina));

        when(alunoRepository.save(any(Aluno.class)))
                .thenReturn(alunoSalvo);

        AlunoResponseDTO response = alunoService.salvar(request);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("João da Silva", response.getAluno());
        assertEquals(2026, response.getAnoIngresso());
        assertEquals(1, response.getSemestreIngresso());
        assertEquals("ATIVO", response.getSituacaoAluno());
        assertEquals(1L, response.getDisciplinaId());
        assertEquals("Sistemas de Informação", response.getCurso());

        verify(disciplinaRepository).findById(1L);
        verify(alunoRepository).save(any(Aluno.class));
    }

    @Test
    void deveLancarExcecaoQuandoDisciplinaNaoEncontrada() {

        AlunoRequestDTO request = new AlunoRequestDTO();

        request.setAluno("João da Silva");
        request.setAnoIngresso(2026);
        request.setSemestreIngresso(1);
        request.setSituacaoAluno("ATIVO");
        request.setDisciplinaId(99L);
        request.setCurso("Sistemas de Informação");

        when(disciplinaRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> alunoService.salvar(request)
        );

        assertEquals("Disciplina não encontrada!", exception.getMessage());

        verify(disciplinaRepository).findById(99L);
        verify(alunoRepository, never()).save(any(Aluno.class));
    }

    @Test
    void deveObterAlunoPorIdComSucesso() {

        Disciplina disciplina = new Disciplina();
        disciplina.setId(1L);

        Aluno aluno = new Aluno();

        aluno.setId(10L);
        aluno.setAluno("João da Silva");
        aluno.setAnoIngresso(2026);
        aluno.setSemestreIngresso(1);
        aluno.setSituacaoAluno("ATIVO");
        aluno.setDisciplina(disciplina);
        aluno.setCurso("Sistemas de Informação");

        when(alunoRepository.findById(10L))
                .thenReturn(Optional.of(aluno));

        AlunoResponseDTO response = alunoService.obterPorId(10L);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("João da Silva", response.getAluno());
        assertEquals(2026, response.getAnoIngresso());
        assertEquals(1, response.getSemestreIngresso());
        assertEquals("ATIVO", response.getSituacaoAluno());
        assertEquals(1L, response.getDisciplinaId());
        assertEquals("Sistemas de Informação", response.getCurso());

        verify(alunoRepository).findById(10L);
    }

    @Test
    void deveLancarExcecaoQuandoAlunoNaoEncontrado() {

        when(alunoRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> alunoService.obterPorId(99L)
        );

        assertEquals("Aluno não encontrado!", exception.getMessage());

        verify(alunoRepository).findById(99L);
    }

    @Test
    void deveListarAlunosComSucesso() {

        Disciplina disciplina = new Disciplina();
        disciplina.setId(1L);

        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setAluno("João da Silva");
        aluno1.setAnoIngresso(2026);
        aluno1.setSemestreIngresso(1);
        aluno1.setSituacaoAluno("ATIVO");
        aluno1.setDisciplina(disciplina);
        aluno1.setCurso("Sistemas de Informação");

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setAluno("Maria da Silva");
        aluno2.setAnoIngresso(2025);
        aluno2.setSemestreIngresso(2);
        aluno2.setSituacaoAluno("ATIVO");
        aluno2.setDisciplina(disciplina);
        aluno2.setCurso("Administração");

        when(alunoRepository.findAll())
                .thenReturn(List.of(aluno1, aluno2));

        List<AlunoResponseDTO> response = alunoService.listar();

        assertNotNull(response);
        assertEquals(2, response.size());

        assertEquals(1L, response.get(0).getId());
        assertEquals("João da Silva", response.get(0).getAluno());

        assertEquals(2L, response.get(1).getId());
        assertEquals("Maria da Silva", response.get(1).getAluno());

        verify(alunoRepository).findAll();
    }

    @Test
    void deveAlterarAlunoComSucesso() {

        Disciplina disciplinaAtual = new Disciplina();
        disciplinaAtual.setId(1L);

        Disciplina novaDisciplina = new Disciplina();
        novaDisciplina.setId(2L);

        Aluno aluno = new Aluno();

        aluno.setId(10L);
        aluno.setAluno("João da Silva");
        aluno.setAnoIngresso(2025);
        aluno.setSemestreIngresso(1);
        aluno.setSituacaoAluno("ATIVO");
        aluno.setDisciplina(disciplinaAtual);
        aluno.setCurso("Administração");

        AlunoRequestDTO request = new AlunoRequestDTO();

        request.setAluno("João da Silva Atualizado");
        request.setAnoIngresso(2026);
        request.setSemestreIngresso(2);
        request.setSituacaoAluno("FORMADO");
        request.setDisciplinaId(2L);
        request.setCurso("Sistemas de Informação");

        when(alunoRepository.findById(10L))
                .thenReturn(Optional.of(aluno));

        when(disciplinaRepository.findById(2L))
                .thenReturn(Optional.of(novaDisciplina));

        when(alunoRepository.save(aluno))
                .thenReturn(aluno);

        AlunoResponseDTO response = alunoService.alterar(10L, request);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("João da Silva Atualizado", response.getAluno());
        assertEquals(2026, response.getAnoIngresso());
        assertEquals(2, response.getSemestreIngresso());
        assertEquals("FORMADO", response.getSituacaoAluno());
        assertEquals(2L, response.getDisciplinaId());
        assertEquals("Sistemas de Informação", response.getCurso());

        verify(alunoRepository).findById(10L);
        verify(disciplinaRepository).findById(2L);
        verify(alunoRepository).save(aluno);
    }

    @Test
    void deveLancarExcecaoAoAlterarAlunoNaoEncontrado() {

        AlunoRequestDTO request = new AlunoRequestDTO();

        request.setAluno("João da Silva");
        request.setAnoIngresso(2026);
        request.setSemestreIngresso(1);
        request.setSituacaoAluno("ATIVO");
        request.setDisciplinaId(1L);
        request.setCurso("Sistemas de Informação");

        when(alunoRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> alunoService.alterar(99L, request)
        );

        assertEquals("Aluno não encontrado!", exception.getMessage());

        verify(alunoRepository).findById(99L);
        verify(alunoRepository, never()).save(any(Aluno.class));
        verifyNoInteractions(disciplinaRepository);
    }

    @Test
    void deveLancarExcecaoAoAlterarQuandoDisciplinaNaoEncontrada() {

        Aluno aluno = new Aluno();
        aluno.setId(10L);

        AlunoRequestDTO request = new AlunoRequestDTO();

        request.setAluno("João da Silva");
        request.setAnoIngresso(2026);
        request.setSemestreIngresso(1);
        request.setSituacaoAluno("ATIVO");
        request.setDisciplinaId(99L);
        request.setCurso("Sistemas de Informação");

        when(alunoRepository.findById(10L))
                .thenReturn(Optional.of(aluno));

        when(disciplinaRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> alunoService.alterar(10L, request)
        );

        assertEquals("Disciplina não encontrada!", exception.getMessage());

        verify(alunoRepository).findById(10L);
        verify(disciplinaRepository).findById(99L);
        verify(alunoRepository, never()).save(any(Aluno.class));
    }

    @Test
    void deveExcluirAlunoComSucesso() {

        Aluno aluno = new Aluno();
        aluno.setId(10L);
        aluno.setAluno("João da Silva");

        when(alunoRepository.findById(10L))
                .thenReturn(Optional.of(aluno));

        alunoService.excluir(10L);

        verify(alunoRepository).findById(10L);
        verify(alunoRepository).delete(aluno);
    }

    @Test
    void deveLancarExcecaoAoExcluirAlunoNaoEncontrado() {

        when(alunoRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> alunoService.excluir(99L)
        );

        assertEquals("Aluno não encontrado!", exception.getMessage());

        verify(alunoRepository).findById(99L);
        verify(alunoRepository, never()).delete(any(Aluno.class));
    }
}
