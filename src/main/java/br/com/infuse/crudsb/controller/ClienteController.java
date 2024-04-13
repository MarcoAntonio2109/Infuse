package br.com.infuse.crudsb.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@PostMapping
	public ResponseEntity<RetornoDTO> cadastraCliente(@RequestBody @Valid ClienteDTO dto) { 
		try {
			return ResponseEntityUtil.defaultResponse(service.cadastraCliente(dto));
		}catch (ClienteException e) {
			logger.error(e.getMessage());
	        return ResponseEntityUtil.defaultResponse(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<RetornoDTO> atualizaCliente(@RequestBody @Valid ClienteDTO dto) { 
		try {
			return ResponseEntityUtil.defaultResponse(service.atualizaCliente(dto));
		}catch (ClienteException e) {
			logger.error(e.getMessage());
	        return ResponseEntityUtil.defaultResponse(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<RetornoDTO> buscaTodosClientes() throws ClienteException {
		return ResponseEntityUtil.defaultResponse(service.buscaTodos());
	}	
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<RetornoDTO> obtemClientePorId(@PathVariable Long id) {		
		return ResponseEntityUtil.defaultResponse(service.obtemClientePorId(id));
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<RetornoDTO> excluiClientePorId(@PathVariable Long id) {	
		
		try {
			service.excluiClientePorId(id);			
		}catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntityUtil.retornaErro(e.getMessage());
		}
		return ResponseEntityUtil.defaultResponse("Cliente excluido com sucesso.");
	}	
	
}
