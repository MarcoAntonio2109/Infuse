package br.com.infuse.crudsb.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.infuse.crudsb.dto.ProdutoDTO;
import br.com.infuse.crudsb.dto.RetornoDTO;
import br.com.infuse.crudsb.exception.ProdutoException;
import br.com.infuse.crudsb.service.ProdutoService;
import br.com.infuse.crudsb.util.ResponseEntityUtil;

@RestController
@RequestMapping(path = "produto")
public class ProdutoController {

	final Logger logger =  LogManager.getLogger(ProdutoController.class.getName());
	
	@Autowired
	private ProdutoService service;
	
	@PostMapping
	public ResponseEntity<RetornoDTO> cadastraProduto(@RequestBody @Valid ProdutoDTO dto) { 
		try {
			return ResponseEntityUtil.defaultResponse(service.cadastraProduto(dto));
		}catch (ProdutoException e) {
			logger.error(e.getMessage());
	        return ResponseEntityUtil.retornaErro(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<RetornoDTO> atualizaCliente(@RequestBody @Valid ProdutoDTO dto) { 
		try {
			return ResponseEntityUtil.defaultResponse(service.atualizaProduto(dto));
		}catch (ProdutoException e) {
			logger.error(e.getMessage());
	        return ResponseEntityUtil.retornaErro(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<RetornoDTO> buscaTodosClientes() throws ProdutoException {
		return ResponseEntityUtil.defaultResponse(service.buscaTodos());
	}	
	
	@GetMapping(path = "{id}")
	public ResponseEntity<RetornoDTO> obtemProdutoPorId(@PathVariable Long id) {		
		return ResponseEntityUtil.defaultResponse(service.obtemProdutoPorId(id));
	}
	
	@DeleteMapping(path = "{id}")
	public ResponseEntity<RetornoDTO> excluiProdutoPorId(@PathVariable Long id) {	
		
		try {
			service.excluiProdutoPorId(id);			
		}catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntityUtil.retornaErro(e.getMessage());
		}
		return ResponseEntityUtil.defaultResponse("Produto excluido com sucesso.");
	}	
	
}
