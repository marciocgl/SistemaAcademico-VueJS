package edu.ifes.ci.si.les.sa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class AlunoTurma implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	private AlunoTurmaPK id = new AlunoTurmaPK();

	@Column(length = 50)
	@NotBlank(message = "O status do aluno deve ser preenchida")
	@Size(min = 2, max = 50, message = "O status do aluno deve ter entre 2 e 50 letras")
	private String status;

	@NotNull(message = "A Nota Avaliativa deve ser preenchida")
	@Digits(integer = 6, fraction = 2, message = "A Nota final deve ser preenchida com dígitos")
	private Double notaFinal;

	@NotNull(message = "A Nota Avaliativa deve ser preenchida")
	@Digits(integer = 6, fraction = 0, message = "A presença final deve ser preenchida com dígitos")
	private Integer presencaFinal;

	@Builder
	public AlunoTurma(Turma turma, Aluno aluno, String status, Double notaFinal, Integer presencaFinal) {
		this.id.setTurma(turma);
		this.id.setAluno(aluno);
		this.status = status;
		this.notaFinal = notaFinal;
		this.presencaFinal = presencaFinal;
	}

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
