package br.com.infuse.crudsb.service;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.infuse.crudsb.dto.ProdutoDTO;
import br.com.infuse.crudsb.entitiy.Produto;
import br.com.infuse.crudsb.exception.ProdutoException;
import br.com.infuse.crudsb.repository.ProdutoRepository;

@Service
public class ProdutoService {

	final Logger logger =  LogManager.getLogger(ProdutoService.class.getName());
	
	@Autowired
	private ProdutoRepository repository;
	
	public Optional<Produto> obtemProdutoPorId(Long codigo) {
		return repository.findById(codigo);
	}

	public Iterable<Produto> buscaTodos() throws ProdutoException{
		try {
			return repository.findAll();	
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new ProdutoException(e.getMessage());		
		}
	}

	public Produto cadastraProduto(@Valid ProdutoDTO dto) throws ProdutoException {
		try {
			return salvaProduto(dto, new Produto());
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new ProdutoException(e.getMessage());			
		}
	}
	
	public Produto atualizaProduto(@Valid ProdutoDTO dto) throws ProdutoException {
		try {
			return repository.findById(dto.getCodigo())
		            .map(produto -> salvaProduto(dto, produto))
		            .orElseGet(() -> salvaProduto(dto, new Produto()));
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new ProdutoException(e.getMessage());			
		}
	}
	
	private Produto salvaProduto(ProdutoDTO dto, Produto produto) {
		produto.setIdProduto(dto.getCodigo());
		produto.setNome(dto.getNome());
		produto.setPreco(dto.getPreco());
		return repository.save(produto);
	}
	
	public void excluiProdutoPorId(Long id) {
		 repository.deleteById(id);
	}
	
}

