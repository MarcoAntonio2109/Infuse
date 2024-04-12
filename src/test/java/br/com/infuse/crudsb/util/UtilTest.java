package br.com.infuse.crudsb.util;

import java.util.Date;

import br.com.infuse.crudsb.dto.ClienteDTO;
import br.com.infuse.crudsb.dto.ProdutoDTO;
import br.com.infuse.crudsb.entities.Cliente;
import br.com.infuse.crudsb.entities.Pedido;
import br.com.infuse.crudsb.entities.Produto;

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
	
}
