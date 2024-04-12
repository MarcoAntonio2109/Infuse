package br.com.infuse.crudsb.service;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.infuse.crudsb.dto.ProdutoDTO;
import br.com.infuse.crudsb.entities.Produto;
import br.com.infuse.crudsb.exception.ProdutoException;
import br.com.infuse.crudsb.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	final Logger logger =  LogManager.getLogger(ProdutoService.class.getName());
	@Autowired
	private ProdutoRepository repository;
	
	public Optional<Produto> obtemProdutoPorId(Long codigo) {
		Optional<Produto> produto = repository.findById(codigo);
		
		return produto;
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
			
			Produto produto = new Produto();
			produto.setNome(dto.getNome());
			produto.setPreco(dto.getPreco());
			return repository.save(produto);
			
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new ProdutoException(e.getMessage());			
		}
		
	}
	
	public Produto atualizaProduto(@Valid ProdutoDTO dto) throws ProdutoException {
		
		try {
			
			Produto produto = new Produto();
			produto.setIdProduto(dto.getCodigo());
			produto.setNome(dto.getNome());
			produto.setPreco(dto.getPreco());
			
		    return repository.findById(dto.getCodigo())
		            .map(entidade -> {
		                entidade.setIdProduto(produto.getIdProduto());
		                entidade.setNome(produto.getNome());
		                entidade.setPreco(produto.getPreco());
		                return repository.save(entidade);
		            })
		            .orElseGet(() -> {
		    			produto.setIdProduto(dto.getCodigo());
		    			produto.setNome(dto.getNome());
		    			produto.setPreco(dto.getPreco());
		                return repository.save(produto);
		            });

			
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new ProdutoException(e.getMessage());			
		}
		
	}
	
	public void excluiProdutoPorId(Long id) {
		 repository.deleteById(id);
	}
	
}
