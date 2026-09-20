package br.com.higo.apiescola.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AlunoResponseDTO {
    private Long id;

    private String aluno;

    private Integer anoIngresso;

    private Integer semestreIngresso;

    private String situacaoAluno;

    private Date dataIngresso;

    private Long disciplinaId;

    private String curso;
}
