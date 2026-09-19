package br.com.higo.apiescola.entity;

import br.com.higo.apiescola.enums.TipoDisciplina;
import br.com.higo.apiescola.enums.TipoNota;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "TB_DISCIPLINA")
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DISCIPLINA")
    private Long id;

    @Column(name = "ATIVA")
    private Boolean ativa;

    @Column(name = "HORA_ATIVIDADE")
    private BigDecimal horaAtividade;

    @Column(name = "HORA_AULA")
    private BigDecimal horaAula;

    @Column(name = "DISCIPLINA")
    private String disciplina;

    @Column(name = "NOTA")
    private BigDecimal nota;

    @Column(name = "TEM_FREQUENCIA")
    private Boolean temFrequencia;

    @Column(name = "TEM_NOTA")
    private Boolean temNota;

    @Enumerated(EnumType.STRING)
    private TipoDisciplina tipoDisciplina;

    @Enumerated(EnumType.STRING)
    private TipoNota tipoNota;
}
