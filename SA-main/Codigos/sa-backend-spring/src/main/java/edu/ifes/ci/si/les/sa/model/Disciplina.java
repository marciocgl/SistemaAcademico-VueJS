package edu.ifes.ci.si.les.sa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 50)
	@NotBlank(message = "O nome da disciplina deve ser preenchido")
	@Size(min = 2, max = 50, message = "O nome da disciplina deve ter entre 2 e 50 letras ou digitos")
	private String nome;

	@NotNull(message = "A carga horária da disciplina deve ser preenchida")
	@Digits(integer = 6, fraction = 0, message = "A carga horária do disciplina deve ser preenchido com dígitos")
	private Integer cargaHoraria;

	@Column(length = 20)
	@NotBlank(message = "O nome reduzido da disciplina deve ser preenchido")
	@Size(min = 2, max = 20, message = "O nome reduzido da disciplina deve ter entre 2 e 20 letras ou digitos")
	private String nomeReduzido;

	@NotNull(message = "O Curso da Turma deve ser preenchido")
	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;

}
