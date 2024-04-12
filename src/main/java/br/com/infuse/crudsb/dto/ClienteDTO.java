package br.com.infuse.crudsb.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {

	private Long codigo;
	@NotBlank(message = "O campo NOME não pode estar vazio.")
	private String nome;
	@NotBlank(message = "O campo CPF não pode estar vazio.")
	private String cpf;
	private String sobrenome;
	private String dataNascimento;

}
