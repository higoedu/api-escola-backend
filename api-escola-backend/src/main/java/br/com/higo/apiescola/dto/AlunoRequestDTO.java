package br.com.higo.apiescola.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlunoRequestDTO {
    @NotBlank(message = "Nome do aluno é obrigatório!nr")
    private String aluno;

    @NotNull(message = "Ano de ingresso do aluno é obrigatório!nr1")
    @Min(value = 1, message = "Ano de ingresso do aluno deve ser maior que zero!nr2")
    private Integer anoIngresso;

    @NotNull(message = "Semestre de ingresso do aluno é obrigatório!nr1")
    @Min(value = 1, message = "Semestre de ingresso do aluno deve ser 1 ou 2!nr2")
    @Max(value = 2, message = "Semestre de ingresso do aluno deve ser 1 ou 2!nr3")
    private Integer semestreIngresso;

    @NotBlank(message = "Situação do aluno é obrigatório!nr")
    private String situacaoAluno;

    @NotNull(message = "disciplinaId é obrigatórionr")
    private Long disciplinaId;

    @NotBlank(message = "Nome do curso é obrigatório!nr")
    private String curso;
}
