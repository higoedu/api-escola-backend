package br.com.higo.apiescola.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "TB_ALUNO")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALUNO")
    private Long id;

    @Basic(optional = false)
    @Column(name = "ALUNO")
    private String aluno;

    @Basic(optional = false)
    @Column(name = "ANO_INGRESSO")
    private Integer anoIngresso;

    @Basic(optional = false)
    @Column(name = "SEMESTRE_INGRESSO")
    private Integer semestreIngresso;

    @Basic(optional = false)
    @Column(name = "SITUACAO_ALUNO")
    private String situacaoAluno;

    @Column(name = "DATA_INGRESSO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataIngresso;

    @ManyToOne
    @JoinColumn(name = "ID_DISCIPLINA")
    private Disciplina disciplina;

    @Column(name = "CURSO")
    private String curso;
}
