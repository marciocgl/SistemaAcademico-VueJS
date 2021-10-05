package edu.ifes.ci.si.les.sa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;


@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
    @Column(length = 50)
    @NotBlank(message = "Nome da Pessoa deve ser preenchido")
    @Size(min = 2, max = 50, message = "Nome da Pessoa deve ter entre 2 e 50 letras")
	private String nome;
    
    @NotNull(message = "Nascimento do Cliente deve ser preenchido")
    @JsonFormat(pattern = "yyyy-MM-dd")
	private Date dataNascimento;
    
    @Column(length = 10)
    @NotBlank(message = "Sexo da Pessoa deve ser preenchido")
    @Size(min = 2, max = 10, message = "Sexo da Pessoa deve ter entre 2 e 50 letras")
	private String sexo;
	
	@Column(length = 50)
    @NotBlank(message = "CPF da Pessoa deve ser preenchido")
    @Size(min = 2, max = 50, message = "CPF da Pessoa deve ter entre 2 e 50 letras")
    @Pattern(regexp="\\d{3}.\\d{3}.\\d{3}-\\d{2}", message = "CPF da Pessoa deve seguir o padrão NNN.NNN.NNN-NN")
	private String cpf;
	
	@Column(length = 50)
    @NotBlank(message = "Rua da Pessoa deve ser preenchido")
    @Size(min = 2, max = 50, message = "Rua da Pessoa deve ter entre 2 e 50 letras")
	private String rua;
	
	@Digits(integer=4, fraction=0, message = "Número da Casa da Pessoa deve ser preenchido com um valor inteiro")
	private Integer numero;
	
	@Digits(integer=1, fraction=0, message = "O tipo do usuário deve ser preenchido com um valor inteiro")
	private Integer tipoUsuario;
	
	@NotNull(message = "O Bairro da Pessoa deve ser preenchido")
    @ManyToOne
    @JoinColumn(name = "bairro_id")
	private Bairro bairro;

}
