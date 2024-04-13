package br.com.infuse.crudsb.util;

import java.util.Date;

import br.com.infuse.crudsb.dto.ClienteDTO;
import br.com.infuse.crudsb.dto.ConsultaPedidoDTO;
import br.com.infuse.crudsb.dto.PedidoDTO;
import br.com.infuse.crudsb.dto.ProdutoDTO;
import br.com.infuse.crudsb.entitiy.Cliente;
import br.com.infuse.crudsb.entitiy.Pedido;
import br.com.infuse.crudsb.entitiy.Produto;

public class UtilTest {

	public static Cliente initCliente() {
		Cliente cli = new Cliente();
		cli.setId(1L);
		cli.setNome("Teste");
		cli.setSobrenome("Teste2");
		cli.setCpf("12345678900");
		
		return cli;
	}
    
	public static Produto initProduto() {
		Produto prod = new Produto();
		prod.setIdProduto(1L);
		prod.setNome("NomeProd");
		prod.setPreco(100.0);
		
		return prod;
	}
	
	public static ProdutoDTO initProdutoDTO() {
		ProdutoDTO prod = new ProdutoDTO();
		prod.setCodigo(1L);
		prod.setNome("NomeProd");
		prod.setPreco(100.0);
		
		return prod;
	}	
	
	public static ClienteDTO initClienteDTO() {
		ClienteDTO cli = new ClienteDTO();
		cli.setCodigo(1L);
		cli.setNome("Teste");
		cli.setSobrenome("Teste2");
		cli.setCpf("12345678900");
		cli.setDataNascimento("1980-10-01");
		return cli;
	}	
	
	public static Pedido initPedido() {
		Pedido pedido = new Pedido();
		pedido.setCliente(initCliente());
		pedido.setProduto(initProduto());
		pedido.setDataCadastro(new Date());
		pedido.setNumControle(123L);
		pedido.setQuantidade(5);
		pedido.setValor(1000.0);
		pedido.setDataCadastro(new Date());
		
		return pedido;
	}
	
	public static PedidoDTO initPedidoDTO() {
        PedidoDTO dto = new PedidoDTO();
        dto.setNumControle(123L);
        dto.setCliente(1L);
        dto.setProduto(1L);
        dto.setQuantidade(5);
		
		return dto;
	}
	
	public static ConsultaPedidoDTO initConsultaPedidoDTO() {
		ConsultaPedidoDTO dto = new ConsultaPedidoDTO();
        dto.setNumControle(123L);
        dto.setCliente(1L);
        dto.setProduto(1L);

		return dto;
	}	

}
