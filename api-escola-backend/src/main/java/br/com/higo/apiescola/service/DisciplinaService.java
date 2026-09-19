package br.com.higo.apiescola.service;

import br.com.higo.apiescola.dto.DisciplinaRequestDTO;
import br.com.higo.apiescola.dto.DisciplinaResponseDTO;
import br.com.higo.apiescola.entity.Disciplina;
import br.com.higo.apiescola.enums.TipoNota;
import br.com.higo.apiescola.repository.DisciplinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {
    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public DisciplinaResponseDTO salvar(DisciplinaRequestDTO request) {
        Disciplina disciplina = new Disciplina();

        disciplina.setAtiva(request.getAtiva());
        disciplina.setHoraAtividade(request.getHoraAtividade());
        disciplina.setHoraAula(request.getHoraAula());
        disciplina.setDisciplina(request.getDisciplina());
        disciplina.setNota(request.getNota());
        disciplina.setTemFrequencia(request.getTemFrequencia());
        disciplina.setTemNota(request.getTemNota());
        disciplina.setTipoDisciplina(request.getTipoDisciplina());
        disciplina.setTipoNota(request.getTipoNota());

        validarDisciplina(disciplina);

        disciplina = disciplinaRepository.save(disciplina);

        return toResponseDTO(disciplina);
    }

    public List<DisciplinaResponseDTO> listar() {
        List<Disciplina> disciplinas = disciplinaRepository.findAll();

        return disciplinas.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public DisciplinaResponseDTO obterPorId(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada!"));

        return toResponseDTO(disciplina);
    }

    public DisciplinaResponseDTO alterar(Long id, DisciplinaRequestDTO request){
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada!"));

        disciplina.setAtiva(request.getAtiva());
        disciplina.setHoraAtividade(request.getHoraAtividade());
        disciplina.setHoraAula(request.getHoraAula());
        disciplina.setDisciplina(request.getDisciplina());
        disciplina.setNota(request.getNota());
        disciplina.setTemFrequencia(request.getTemFrequencia());
        disciplina.setTemNota(request.getTemNota());
        disciplina.setTipoDisciplina(request.getTipoDisciplina());
        disciplina.setTipoNota(request.getTipoNota());

        validarDisciplina(disciplina);

        disciplina = disciplinaRepository.save(disciplina);

        return toResponseDTO(disciplina);
    }

    public void excluir(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada!"));

        disciplinaRepository.delete(disciplina);
    }

    private void validarDisciplina(Disciplina disciplina) {
        if (disciplina.getAtiva() == null) {
            throw new RuntimeException("Ativa é obrigatória!");
        }

        if (disciplina.getHoraAtividade() == null) {
            throw new RuntimeException("Hora da atividade é obrigatória!");
        }

        if (disciplina.getHoraAula() == null) {
            throw new RuntimeException("Hora aula é obrigatória!");
        }

        if (disciplina.getDisciplina() == null || disciplina.getDisciplina().isBlank()) {
            throw new RuntimeException("Nome da disciplina é obrigatório!");
        }

        if (disciplina.getTemFrequencia() == null) {
            throw new RuntimeException("O campo 'Tem frequência' é obrigatório!");
        }

        if (disciplina.getTemNota() == null) {
            throw new RuntimeException("O campo 'Tem nota' é obrigatório!");
        }

        if (Boolean.TRUE.equals(disciplina.getTemNota())) {

            if (disciplina.getNota() == null) {
                throw new RuntimeException(
                        "Nota é obrigatória quando a disciplina possui nota!"
                );
            }

        } else {

            if (disciplina.getNota() != null) {
                throw new RuntimeException(
                        "A nota não deve ser informada quando a disciplina não possui nota!"
                );
            }
        }

        if (disciplina.getTipoDisciplina() == null) {
            throw new RuntimeException("Tipo de disciplina é obrigatório!");
        }

        if (disciplina.getTipoNota() == null) {
            throw new RuntimeException("Tipo de nota é obrigatório!");
        }

        if (disciplina.getTipoNota() == TipoNota.CONCEITO
                && Boolean.TRUE.equals(disciplina.getTemNota())) {

            throw new IllegalArgumentException(
                    "Uma disciplina com avaliação por conceito não pode possuir nota."
            );
        }

        if (disciplina.getTipoNota() == TipoNota.NOTA
                && Boolean.FALSE.equals(disciplina.getTemNota())) {

            throw new IllegalArgumentException(
                    "Uma disciplina com avaliação por nota deve possuir nota."
            );
        }
    }

    private DisciplinaResponseDTO toResponseDTO(Disciplina disciplina) {
        DisciplinaResponseDTO dto = new DisciplinaResponseDTO();

        dto.setId(disciplina.getId());
        dto.setAtiva(disciplina.getAtiva());
        dto.setHoraAtividade(disciplina.getHoraAtividade());
        dto.setHoraAula(disciplina.getHoraAula());
        dto.setDisciplina(disciplina.getDisciplina());
        dto.setNota(disciplina.getNota());
        dto.setTemFrequencia(disciplina.getTemFrequencia());
        dto.setTemNota(disciplina.getTemNota());
        dto.setTipoDisciplina(disciplina.getTipoDisciplina());
        dto.setTipoNota(disciplina.getTipoNota());

        return dto;
    }
}
