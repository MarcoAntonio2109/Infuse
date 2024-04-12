package br.com.infuse.crudsb.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {

	private Long codigo;
	@NotBlank(message = "O campo NOME PRODUTO não pode estar vazio.")
	private String nome;
	@NotNull(message = "O campo PRECO não pode estar vazio.")
	private Double preco;
	
}
