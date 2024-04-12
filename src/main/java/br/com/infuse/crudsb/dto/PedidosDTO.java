package br.com.infuse.crudsb.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidosDTO {
	
	@NotBlank(message = "O campo NUMERO CONTROLE não pode estar vazio.")
	private Long numControle;
	@NotBlank(message = "O campo PRODUTO não pode estar vazio.")
	private Long produto;
	@Max(10)
	private int quantidade;
	@NotBlank(message = "O campo CLIENTE não pode estar vazio.")
	private Long cliente;
	private Date data;


}
