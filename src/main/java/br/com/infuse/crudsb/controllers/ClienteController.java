package br.com.infuse.crudsb.controllers;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.infuse.crudsb.dto.ClienteDTO;
import br.com.infuse.crudsb.dto.RetornoDTO;
import br.com.infuse.crudsb.exception.ClienteException;
import br.com.infuse.crudsb.service.ClienteService;
import br.com.infuse.crudsb.util.ResponseEntityUtil;

@RestController
@RequestMapping(path = "cliente")
@Validated
public class ClienteController {

	final Logger logger =  LogManager.getLogger(ClienteController.class.getName());
	
	@Autowired
	private ClienteService service;
	
	@PostMapping(path = "/cadastra-cliente")
	public ResponseEntity<RetornoDTO> cadastraCliente(@RequestBody @Valid ClienteDTO dto) { 
		try {
			return ResponseEntityUtil.defaultResponse(service.cadastraCliente(dto));
		}catch (ClienteException e) {
			logger.error(e.getMessage());
	        return ResponseEntityUtil.defaultResponse(e.getMessage());
		}
	}
	
	@PutMapping(path = "/atualiza-cliente")
	public ResponseEntity<RetornoDTO> atualizaCliente(@RequestBody @Valid ClienteDTO dto) { 
		try {
			return ResponseEntityUtil.defaultResponse(service.atualizaCliente(dto));
		}catch (ClienteException e) {
			logger.error(e.getMessage());
	        return ResponseEntityUtil.defaultResponse(e.getMessage());
		}
	}
	
	@GetMapping(path = "/busca-todos-clientes")
	public ResponseEntity<RetornoDTO> buscaTodosClientes() throws ClienteException {
		return ResponseEntityUtil.defaultResponse(service.buscaTodos());
	}	
	
	@GetMapping(path = "/obtem-cliente-por-id")
	public ResponseEntity<RetornoDTO> obtemClientePorId(@RequestParam(name = "id", required=true) Long id) {		
		return ResponseEntityUtil.defaultResponse(service.obtemClientePorId(id));
	}
	
	@DeleteMapping(path = "/exclui-cliente-por-id")
	public ResponseEntity<RetornoDTO> excluiClientePorId(@RequestParam(name = "id", required=true) Long id) {	
		
		try {
			service.excluiClientePorId(id);			
		}catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntityUtil.defaultResponse("Erro ao excluir Cliente.");
		}
		return ResponseEntityUtil.defaultResponse("Cliente excluido com sucesso.");
	}	
	
}
