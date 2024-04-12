package br.com.infuse.crudsb.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.infuse.crudsb.dto.PedidosDTO;
import br.com.infuse.crudsb.dto.RetornoDTO;
import br.com.infuse.crudsb.exception.PedidoException;
import br.com.infuse.crudsb.service.PedidoService;
import br.com.infuse.crudsb.util.ResponseEntityUtil;

@RestController
@RequestMapping(path = "pedido")
public class PedidoController {

	final Logger logger =  LogManager.getLogger(PedidoController.class.getName());
	
	@Autowired
	private PedidoService service;
	
	@GetMapping(path = "/busca-pedidos-por-parametros")
	public ResponseEntity<RetornoDTO> buscaPedidos(@RequestBody @Valid PedidosDTO dto) {		
		try {	
			return ResponseEntityUtil.defaultResponse(service.buscaPedido(dto));
		}catch (PedidoException e) {
			logger.error(e.getMessage());
			 return ResponseEntityUtil.defaultResponse(e.getMessage());
		}
	}	
	
	@GetMapping(path = "/busca-pedido-por-id")
	public ResponseEntity<RetornoDTO> findById(@RequestParam(name = "id", required=true) Long id) throws PedidoException {		
		return ResponseEntityUtil.defaultResponse(service.findById(id));
	}	
	
	@PostMapping(path = "/realiza-pedido")
	public ResponseEntity<RetornoDTO> criaPedido(@RequestBody @Valid PedidosDTO dto) { 
		try {
			return ResponseEntityUtil.defaultResponse(service.criaPedido(dto));
		}catch (PedidoException e) {
			logger.error(e.getMessage());
	        return ResponseEntityUtil.defaultResponse(e.getMessage());
		}
	}	
	
    @PostMapping("/realiza-pedido-xml")
    public ResponseEntity<RetornoDTO> uploadFile(@RequestParam("file") MultipartFile file) {
    	String xml = "";
        if (file.isEmpty()) {
            return ResponseEntityUtil.defaultResponse("O arquivo enviado está vazio");
        }
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
        	
            String line;
            while ((line = br.readLine()) != null) {
            	xml = xml.concat(line);
            }
            return ResponseEntityUtil.defaultResponse(service.criaPedidoXml(xml));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntityUtil.defaultResponse(e.getMessage());
        }
        
    }
	
}
