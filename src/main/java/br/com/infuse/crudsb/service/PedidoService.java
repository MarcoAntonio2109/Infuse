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

import br.com.infuse.crudsb.dto.ConsultaPedidoDTO;
import br.com.infuse.crudsb.dto.PedidoDTO;
import br.com.infuse.crudsb.entitiy.Cliente;
import br.com.infuse.crudsb.entitiy.Pedido;
import br.com.infuse.crudsb.entitiy.Produto;
import br.com.infuse.crudsb.exception.PedidoException;
import br.com.infuse.crudsb.mapper.PedidoMapperXML;
import br.com.infuse.crudsb.repository.PedidoRepository;
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
	
	public List<Pedido> buscaPedido (ConsultaPedidoDTO dto) throws PedidoException {		
		Specification<Pedido> specification = new PedidoSpecification(dto);
		return repository.findAll(specification);		
	}
	
	public Pedido criaPedidoXml(String xml) throws JAXBException, PedidoException {
        PedidoMapperXML pedidoMapperXML = unmarshalXml(xml);
        PedidoDTO dto = mapXmlToDto(pedidoMapperXML);
        return criaPedido(dto);
	}

	public Pedido criaPedido (PedidoDTO dto) throws PedidoException {
		validaNumControleExistente(dto.getNumControle());
		Cliente cliente = obtemCliente(dto.getCliente());
		Produto produto = obtemProduto(dto.getProduto());
		validaQuantidade(dto.getQuantidade());
		return criaEPersistePedido(dto, cliente, produto);
	}

	private PedidoMapperXML unmarshalXml(String xml) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(PedidoMapperXML.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (PedidoMapperXML) jaxbUnmarshaller.unmarshal(new StringReader(xml));
	}

	private PedidoDTO mapXmlToDto(PedidoMapperXML pedidoMapperXML) {
		PedidoDTO dto = new PedidoDTO();
        dto.setCliente(pedidoMapperXML.getCliente());
        dto.setProduto(pedidoMapperXML.getProduto());
        dto.setNumControle(pedidoMapperXML.getNumControle());
        dto.setQuantidade(pedidoMapperXML.getQuantidade());
        dto.setData(Util.converterStringParaData(pedidoMapperXML.getData()));
        return dto;
	}

	private void validaNumControleExistente(Long numControle) throws PedidoException {
		if(repository.validaNumControleExistente(numControle) > 0 ){
			throw new PedidoException("Número de controle Informado no pedido já existe.");
		}
	}

	private Cliente obtemCliente(Long idCliente) throws PedidoException {
		Optional<Cliente> cliente = clienteService.obtemClientePorId(idCliente);
		if(!cliente.isPresent()) {
			throw new PedidoException("Cliente Informado no pedido não existe.");
		}
		return cliente.get();
	}

	private Produto obtemProduto(Long idProduto) throws PedidoException {
		Optional<Produto> produto = produtoService.obtemProdutoPorId(idProduto);
		if(!produto.isPresent()) {
			throw new PedidoException("Produto Informado no pedido não existe.");
		}
		return produto.get();
	}

	private void validaQuantidade(int quantidade) throws PedidoException {
		if(quantidade > 10) {
			throw new PedidoException("Limite máximo de produtos excedido.");
		}
	}

	private Pedido criaEPersistePedido(PedidoDTO dto, Cliente cliente, Produto produto) {
		Pedido pedido = new Pedido();
		pedido.setNumControle(dto.getNumControle());
		pedido.setCliente(cliente);
		pedido.setProduto(produto);
		pedido.setQuantidade(dto.getQuantidade());
		pedido.setValor(calculaPrecoFinal(dto.getQuantidade(), produto.getPreco()));
		pedido.setDataCadastro(dto.getData() != null ? dto.getData() : new Date());
		return repository.save(pedido);
	}

	private Double calculaPrecoFinal(int quantidade, Double preco) {
		double desconto = quantidade > 5 && quantidade < 10 ? 0.05 : quantidade == 10 ? 0.10 : 0;
		double precoComDesconto = preco * (1 - desconto);
		return precoComDesconto * quantidade;
	}

	public Optional<Pedido> findById(Long id) {
		return repository.findById(id);
	}
}

