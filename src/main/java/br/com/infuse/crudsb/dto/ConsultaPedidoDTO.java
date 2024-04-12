package br.com.infuse.crudsb.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ConsultaPedidoDTO {


	private Long numControle;
	private Long produto;
	private Long cliente;
	private Date data;	
	
}
