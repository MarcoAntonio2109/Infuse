package br.com.infuse.crudsb.controllers;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping(path = "/cadastra-produto")
	public ResponseEntity<RetornoDTO> cadastraProduto(@RequestBody @Valid ProdutoDTO dto) { 
		try {
			return ResponseEntityUtil.defaultResponse(service.cadastraProduto(dto));
		}catch (ProdutoException e) {
			logger.error(e.getMessage());
	        return ResponseEntityUtil.defaultResponse(e.getMessage());
		}
	}
	
	@PutMapping(path = "/atualiza-produto")
	public ResponseEntity<RetornoDTO> atualizaCliente(@RequestBody @Valid ProdutoDTO dto) { 
		try {
			return ResponseEntityUtil.defaultResponse(service.atualizaProduto(dto));
		}catch (ProdutoException e) {
			logger.error(e.getMessage());
	        return ResponseEntityUtil.defaultResponse(e.getMessage());
		}
	}
	
	@GetMapping(path = "/busca-todos-produtos")
	public ResponseEntity<RetornoDTO> buscaTodosClientes() throws ProdutoException {
		return ResponseEntityUtil.defaultResponse(service.buscaTodos());
	}	
	
	@GetMapping(path = "/obtem-produto-por-id")
	public ResponseEntity<RetornoDTO> obtemProdutoPorId(@RequestParam(name = "id", required=true) Long id) {		
		return ResponseEntityUtil.defaultResponse(service.obtemProdutoPorId(id));
	}
	
	@DeleteMapping(path = "/exclui-produto-por-id")
	public ResponseEntity<RetornoDTO> excluiProdutoPorId(@RequestParam(name = "id", required=true) Long id) {	
		
		try {
			service.excluiProdutoPorId(id);			
		}catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntityUtil.defaultResponse("Erro ao excluir Produto.");
		}
		return ResponseEntityUtil.defaultResponse("Produto excluido com sucesso.");
	}	
	
}
