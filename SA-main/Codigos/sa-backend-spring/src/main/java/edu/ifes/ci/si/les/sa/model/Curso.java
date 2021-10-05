package edu.ifes.ci.si.les.sa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@EqualsAndHashCode(of = {"id"})
public class Curso implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(length = 50)
    @NotBlank(message = "O nome do curso deve ser preenchido")
    @Size(min = 2, max = 50, message = "O nome do Curso deve ter entre 2 e 50 letras")
	private String nome;
	
	@NotNull(message = "A carga horária do Curso deve ser preenchida")
    @Digits(integer=6, fraction=0, message = "A carga horária do Curso deve ser preenchido com dígitos")
	private Integer cargaHoraria;
	
	@NotNull(message = "A duracao do Curso deve ser preenchida")
    @Digits(integer=6, fraction=0, message = "A duracao do Curso deve ser preenchido com dígitos")
	private Integer duracao;
	
	@Column(length = 50)
    @NotBlank(message = "O Tipo do curso deve ser preenchido")
    @Size(min = 2, max = 50, message = "O tipo do Curso deve ter entre 2 e 50 letras")
	private String tipo;

}
