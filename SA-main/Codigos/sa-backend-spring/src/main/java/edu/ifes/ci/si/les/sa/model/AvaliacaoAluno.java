package edu.ifes.ci.si.les.sa.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class AvaliacaoAluno implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	private AvaliacaoAlunoPK id = new AvaliacaoAlunoPK();

	@NotNull(message = "A Nota Avaliativa deve ser preenchida")
	@Digits(integer = 6, fraction = 2, message = "A Nota Avaliativa deve ser preenchida com dígitos")
	private Double nota;

	@Builder
	public AvaliacaoAluno(Turma turma, Aluno aluno, Double nota) {
		this.id.setTurma(turma);
		this.id.setAluno(aluno);
		this.nota = nota;
	}

	@JsonIgnore
	public Turma getTurma() {
		return id.getTurma();
	}

	public void setTurma(Turma turma) {
		id.setTurma(turma);
	}

	public Aluno getAluno() {
		return id.getAluno();
	}

	public void setAluno(Aluno aluno) {
		id.setAluno(aluno);
	}

}
