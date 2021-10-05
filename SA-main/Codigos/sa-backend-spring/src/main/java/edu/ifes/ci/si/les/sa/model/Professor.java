package edu.ifes.ci.si.les.sa.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;


@Entity
@Data 
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Professor extends Pessoa {
	private static final long serialVersionUID = 1L;
	
	@Column(length = 50)
    @Size(min = 2, max = 50, message = "A titulação do professor deve ter entre 2 e 50 letras")
	private String titulacao;

	@Column(length = 50)
    @NotBlank(message = "E-mail do Professor deve ser preenchido")
    @Size(min = 2, max = 50, message = "E-mail do Professor deve ter entre 2 e 50 letras")
    @Pattern(regexp="\\\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,4}\\\\b", message = "O E-mail do Professor deve seguir o padrão NNNNN-NN")
	private String email;
	
	@Column(length = 20)
    @NotBlank(message = "Senha do Professor deve ser preenchida")
    @Size(min = 6, max = 10, message = "Senha do Professor deve ter entre 6 e 20 caracteres")
	private String senha;

	@Column(length = 15)
	  @NotBlank(message = "O Campo deve ser preenchido")
	  @Size(min = 14, max = 15, message = "O campo deve ter pelo menos 14 digitos")
	  private String telefone;
    
    @Builder
    public Professor(Integer id, String nome, Date dataNascimento, String sexo, String cpf, String rua, Integer numero, Integer tipoUsuario, Bairro bairro, String titulacao, String email, String senha, String telefone) {
    	super(id, nome, dataNascimento, sexo, cpf, rua, numero, tipoUsuario, bairro);
    	this.titulacao = titulacao;
    	this.email = email;
    	this.senha = senha;
    	this.telefone = telefone;
    }

}
