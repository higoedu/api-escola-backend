package br.com.higo.apiescola.service;

import br.com.higo.apiescola.dto.AlunoRequestDTO;
import br.com.higo.apiescola.dto.AlunoResponseDTO;
import br.com.higo.apiescola.entity.Aluno;
import br.com.higo.apiescola.entity.Disciplina;
import br.com.higo.apiescola.repository.AlunoRepository;
import br.com.higo.apiescola.repository.DisciplinaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;

    public AlunoService(AlunoRepository alunoRepository, DisciplinaRepository disciplinaRepository) {
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public AlunoResponseDTO salvar(AlunoRequestDTO request) {
        Aluno aluno = new Aluno();

        aluno.setAluno(request.getAluno());
        aluno.setAnoIngresso(request.getAnoIngresso());
        aluno.setSemestreIngresso(request.getSemestreIngresso());
        aluno.setSituacaoAluno(request.getSituacaoAluno());
        aluno.setDataIngresso(new Date());

        if (request.getDisciplinaId() == null) {
            throw new IllegalArgumentException("disciplinaId é obrigatório");
        }

        Disciplina disciplina = disciplinaRepository
                .findById(request.getDisciplinaId())
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada!"));

        aluno.setDisciplina(disciplina);
        aluno.setCurso(request.getCurso());

        validarAluno(aluno);

        aluno = alunoRepository.save(aluno);

        return toResponseDTO(aluno);
    }

    public List<AlunoResponseDTO> listar() {
        return alunoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public AlunoResponseDTO obterPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        return toResponseDTO(aluno);
    }

    public AlunoResponseDTO alterar(Long id, AlunoRequestDTO request){
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        aluno.setAluno(request.getAluno());
        aluno.setAnoIngresso(request.getAnoIngresso());
        aluno.setSemestreIngresso(request.getSemestreIngresso());
        aluno.setSituacaoAluno(request.getSituacaoAluno());

        if (request.getDisciplinaId() == null) {
            throw new IllegalArgumentException("disciplinaId é obrigatório");
        }

        Disciplina disciplina = disciplinaRepository
                .findById(request.getDisciplinaId())
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada!"));

        aluno.setDisciplina(disciplina);
        aluno.setCurso(request.getCurso());

        validarAluno(aluno);

        aluno = alunoRepository.save(aluno);

        return toResponseDTO(aluno);
    }

    public void excluir(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        this.alunoRepository.delete(aluno);
    }

    private void validarAluno (Aluno aluno) {
        if(aluno.getAluno() == null || aluno.getAluno().isBlank()){
            throw new RuntimeException("Nome do aluno é obrigatório!");
        }

        if(aluno.getAnoIngresso() == null || aluno.getAnoIngresso() <= 0){
            throw new RuntimeException("Ano de ingresso do aluno é obrigatório!");
        }

        if(aluno.getSemestreIngresso() != 1 && aluno.getSemestreIngresso() != 2){
            throw new RuntimeException("Semestre de ingresso do aluno deve ser 1 ou 2!");
        }

        if(aluno.getSituacaoAluno() == null || aluno.getSituacaoAluno().isBlank()){
            throw new RuntimeException("Situação do aluno é obrigatório!");
        }

        if(aluno.getDisciplina() == null){
            throw new RuntimeException("Disciplina é obrigatória!");
        }

        if(aluno.getCurso() == null || aluno.getCurso().isBlank()){
            throw new RuntimeException("Nome do curso é obrigatório!");
        }
    }

    private AlunoResponseDTO toResponseDTO(Aluno aluno) {
        AlunoResponseDTO dto = new AlunoResponseDTO();

        dto.setId(aluno.getId());
        dto.setAluno(aluno.getAluno());
        dto.setAnoIngresso(aluno.getAnoIngresso());
        dto.setSemestreIngresso(aluno.getSemestreIngresso());
        dto.setSituacaoAluno(aluno.getSituacaoAluno());
        dto.setDataIngresso(aluno.getDataIngresso());

        if (aluno.getDisciplina() != null) {
            dto.setDisciplinaId(aluno.getDisciplina().getId());
        }

        dto.setCurso(aluno.getCurso());

        return dto;
    }
}
