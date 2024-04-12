package br.com.infuse.crudsb.service;

import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.infuse.crudsb.dto.PedidoMapperXML;
import br.com.infuse.crudsb.dto.PedidosDTO;
import br.com.infuse.crudsb.entities.Cliente;
import br.com.infuse.crudsb.entities.Pedido;
import br.com.infuse.crudsb.entities.Produto;
import br.com.infuse.crudsb.exception.PedidoException;
import br.com.infuse.crudsb.repositories.PedidoRepository;
import br.com.infuse.crudsb.specification.PedidoSpecification;
import br.com.infuse.crudsb.util.Util;

@Service
public class PedidoService {

	
	final Logger logger =  LogManager.getLogger(PedidoService.class.getName());
	
	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	public List<Pedido> buscaPedido (PedidosDTO dto) throws PedidoException {		
		Specification<Pedido> specification = new PedidoSpecification(dto);
		return repository.findAll(specification);		
	}
	
	public Pedido criaPedidoXml(String xml) throws JAXBException, PedidoException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PedidoMapperXML.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        PedidoMapperXML pedidoMapperXML = (PedidoMapperXML) jaxbUnmarshaller.unmarshal(new StringReader(xml));

        PedidosDTO dto = new PedidosDTO();
        dto.setCliente(pedidoMapperXML.getCliente());
        dto.setProduto(pedidoMapperXML.getProduto());
        dto.setNumControle(pedidoMapperXML.getNumControle());
        dto.setQuantidade(pedidoMapperXML.getQuantidade());
        
        dto.setData(Util.converterStringParaData(pedidoMapperXML.getData()));
        
        return criaPedido(dto);
	}

	public Pedido criaPedido (PedidosDTO dto) throws PedidoException {

		Pedido pedido = new Pedido();

		try {
			if(repository.validaNumControleExistente(dto.getNumControle()) > 0 ){
				throw new PedidoException("Número de controle Informado no pedido já existe.");
			}
			pedido.setNumControle(dto.getNumControle());
			Optional<Cliente> cliente = clienteService.obtemClientePorId(dto.getCliente());
			if(!cliente.isPresent()) {
				throw new PedidoException("Cliente Informado no pedido não existe.");
			}
			pedido.setCliente(cliente.get());
			
			Optional<Produto> produto = produtoService.obtemProdutoPorId(dto.getProduto());
			if(!produto.isPresent()) {
				throw new PedidoException("Produto Informado no pedido não existe.");
			}
			
			pedido.setProduto(produto.get());

			if((dto.getQuantidade() > 10) ){
				throw new PedidoException("Limite máximo de produtos excedido.");
			}
			pedido.setQuantidade(dto.getQuantidade());
			pedido.setValor(calculaPrecoFinal(dto.getQuantidade(), produto.get().getPreco()));
			if(dto.getData() != null) {
				pedido.setDataCadastro(dto.getData());
			}else {
				pedido.setDataCadastro(new Date());
			}
	
			return repository.save(pedido);
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new PedidoException(e.getMessage());			
		}
		
	}

	private Double calculaPrecoFinal(int quantidade, Double preco) {

		if(quantidade > 5 && quantidade < 10) {
			
			double precoComDesconto = preco * (1 - 0.05);
			
			return precoComDesconto * quantidade;
		}else if(quantidade == 10) {
			double precoComDesconto = preco * (1 - 0.10);
			
			return precoComDesconto * quantidade;
			
		}
		return (quantidade * preco);
	}

	public Optional<Pedido> findById(Long id) {
		return repository.findById(id);
	}
	
	
}
