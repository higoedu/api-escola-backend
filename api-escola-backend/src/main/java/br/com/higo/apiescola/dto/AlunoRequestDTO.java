package br.com.higo.apiescola.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
