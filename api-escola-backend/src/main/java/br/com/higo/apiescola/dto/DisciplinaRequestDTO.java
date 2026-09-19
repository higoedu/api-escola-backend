package br.com.higo.apiescola.dto;

import br.com.higo.apiescola.enums.TipoDisciplina;
import br.com.higo.apiescola.enums.TipoNota;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DisciplinaRequestDTO {
    private Boolean ativa;

    private String disciplina;

    private BigDecimal horaAtividade;

    private BigDecimal horaAula;

    private BigDecimal nota;

    private Boolean temFrequencia;

    private Boolean temNota;

    private TipoDisciplina tipoDisciplina;

    private TipoNota tipoNota;
}
