package br.com.higo.apiescola.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AlunoRequestDTO {
    private String aluno;

    private Integer anoIngresso;

    private Integer semestreIngresso;

    private String situacaoAluno;

    private Long disciplinaId;

    private String curso;
}
