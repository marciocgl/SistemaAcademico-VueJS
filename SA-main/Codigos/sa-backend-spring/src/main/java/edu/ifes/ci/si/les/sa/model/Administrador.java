package edu.ifes.ci.si.les.sa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity 
@Data 
@AllArgsConstructor 
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Administrador extends Pessoa {
	
	private static final long serialVersionUID = 1L;
	
	@Column(length = 50)
    @NotBlank(message = "E-mail do Administrador deve ser preenchido")
    @Size(min = 2, max = 50, message = "E-mail do Administrador deve ter entre 2 e 50 letras")
    @Pattern(regexp="\\\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,4}\\\\b", message = "O E-mail do Administrador deve seguir o padrão NNNNN-NN")
	private String email;
	
	@Column(length = 20)
    @NotBlank(message = "Senha do Administrador deve ser preenchida")
    @Size(min = 6, max = 10, message = "Senha do Administrador deve ter entre 6 e 20 caracteres")
	private String senha;
	
	public Administrador(Integer id, String nome, Date dataNascimento, String sexo, String cpf, String rua, Integer numero, Integer tipoUsuario, Bairro bairro, String email, String senha) {
		super(id, nome, dataNascimento, sexo, cpf, rua, numero, tipoUsuario, bairro);
		this.email = email;
		this.senha = senha;
	}

}
