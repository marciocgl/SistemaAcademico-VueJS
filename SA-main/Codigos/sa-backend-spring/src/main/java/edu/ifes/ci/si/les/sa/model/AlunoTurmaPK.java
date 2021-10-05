package edu.ifes.ci.si.les.sa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.*;

//Classe criada para representar a chave primária composta do objeto AlunoTurma
@Embeddable
@Data

@EqualsAndHashCode(of = { "aluno", "turma" })
@AllArgsConstructor
@NoArgsConstructor
public class AlunoTurmaPK implements Serializable {

	private static final long serialVersionUID = 1L;

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
