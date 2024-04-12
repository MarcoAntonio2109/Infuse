package br.com.infuse.crudsb.service;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.infuse.crudsb.dto.ClienteDTO;
import br.com.infuse.crudsb.entities.Cliente;
import br.com.infuse.crudsb.exception.ClienteException;
import br.com.infuse.crudsb.repositories.ClienteRepository;
import br.com.infuse.crudsb.util.Util;

@Service
public class ClienteService {
	final Logger logger =  LogManager.getLogger(ClienteService.class.getName());
	@Autowired
	private ClienteRepository repository;

	public Iterable<Cliente> buscaTodos() throws ClienteException{
		try {
			return repository.findAll();	
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new ClienteException(e.getMessage());		
		}
	}

	public Optional<Cliente> obtemClientePorId(Long codigo) {
		return repository.findById(codigo);
	}

	public Cliente cadastraCliente(@Valid ClienteDTO dto) throws ClienteException {
		try {
			if(repository.validaCpfExistente(dto.getCpf())>0) {
				throw new ClienteException("Número de controle Informado no pedido já existe.");
			}
			Cliente cliente = new Cliente();
			cliente.setCpf(dto.getCpf());
			cliente.setNome(dto.getNome());
			cliente.setSobrenome(dto.getSobrenome());
			cliente.setDataNascimento(Util.converterStringParaData(dto.getDataNascimento()));
			return repository.save(cliente);
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new ClienteException(e.getMessage());			
		}
		
	}

	public Cliente atualizaCliente(@Valid ClienteDTO dto) throws ClienteException {
		try {
			if(dto.getCodigo() == null) {
				throw new ClienteException("Código do Cliente não informado.");
			}
			if(repository.validaCpfExistente(dto.getCpf())>0) {
				throw new ClienteException("Número do CPF Informado no pedido já existe.");
			}
			Optional<Cliente> cliente = repository.findById(dto.getCodigo());
			if(!cliente.isPresent()) {
				throw new ClienteException("Código do Cliente informado não existe.");
			}
			cliente.get().setCpf(dto.getCpf());
			cliente.get().setNome(dto.getNome());
			cliente.get().setSobrenome(dto.getSobrenome());
			cliente.get().setDataNascimento(Util.converterStringParaData(dto.getDataNascimento()));

			return repository.save(cliente.get());
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new ClienteException(e.getMessage());			
		}
	
	}

	public void excluiClientePorId(Long id) {
		 repository.deleteById(id);
	}
}
