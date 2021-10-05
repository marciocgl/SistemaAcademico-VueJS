package edu.ifes.ci.si.les.sa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Classe criada para representar a chave primária composta do objeto PresencaAluno
@Embeddable
@Data
@EqualsAndHashCode(of = {"aula", "aluno", "turma"})
@AllArgsConstructor
@NoArgsConstructor
public class PresencaAlunoPK implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "aula_id")
	private Aula aula;
	
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    @Getter
    @Setter
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    @Getter
    @Setter
    private Turma turma;

}
