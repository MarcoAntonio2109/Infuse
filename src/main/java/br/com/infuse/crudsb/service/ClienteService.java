package br.com.infuse.crudsb.service;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.infuse.crudsb.dto.ClienteDTO;
import br.com.infuse.crudsb.entitiy.Cliente;
import br.com.infuse.crudsb.exception.ClienteException;
import br.com.infuse.crudsb.repository.ClienteRepository;
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
				throw new ClienteException("Número de CPF informado já existe.");
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
			validaCodigoCliente(dto.getCodigo());
			Optional<Cliente> cliente = repository.findById(dto.getCodigo());
			validaCpfCliente(dto, cliente.get());
			atualizaDadosCliente(dto, cliente.get());

			return repository.save(cliente.get());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ClienteException(e.getMessage());
		}
	}

	private void validaCodigoCliente(Long codigo) throws ClienteException {
		if(codigo == null) {
			throw new ClienteException("Código do Cliente não informado.");
		}
		if(!repository.existsById(codigo)) {
			throw new ClienteException("Código do Cliente informado não existe.");
		}
	}

	private void validaCpfCliente(ClienteDTO dto, Cliente cliente) throws ClienteException {
	    if (!dto.getCpf().equals(cliente.getCpf())) {
	        if (repository.validaCpfExistente(dto.getCpf()) > 0) {
	            throw new ClienteException("Número do CPF Informado já existe.");
	        }
	    }
	}

	private void atualizaDadosCliente(ClienteDTO dto, Cliente cliente) {
		cliente.setCpf(dto.getCpf());
		cliente.setNome(dto.getNome());
		cliente.setSobrenome(dto.getSobrenome());
		cliente.setDataNascimento(Util.converterStringParaData(dto.getDataNascimento()));
	}

	public void excluiClientePorId(Long id) {
		 repository.deleteById(id);
	}
}
