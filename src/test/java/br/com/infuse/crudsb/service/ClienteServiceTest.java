package br.com.infuse.crudsb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.infuse.crudsb.dto.ClienteDTO;
import br.com.infuse.crudsb.entities.Cliente;
import br.com.infuse.crudsb.exception.ClienteException;
import br.com.infuse.crudsb.repositories.ClienteRepository;
import br.com.infuse.crudsb.service.ClienteService;
import br.com.infuse.crudsb.util.Util;
import br.com.infuse.crudsb.util.UtilTest;

public class ClienteServiceTest {

	   @InjectMocks
	    private ClienteService service;

	    @Mock
	    private ClienteRepository repository;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.initMocks(this);
	    }
	    
	    @Test
	    public void testAtualizaCliente() throws ClienteException {
	    	
	        ClienteDTO dto = UtilTest.initClienteDTO();
	        Cliente cliente = UtilTest.initCliente();

	        when(repository.findById(dto.getCodigo())).thenReturn(Optional.of(cliente));
	        when(repository.validaCpfExistente(dto.getCpf())).thenReturn(0);
	        when(repository.save(any(Cliente.class))).thenAnswer(i -> i.getArguments()[0]);

	        Cliente clienteAtualizado = service.atualizaCliente(dto);

	        assertEquals(clienteAtualizado.getId(), dto.getCodigo());
	        assertEquals(clienteAtualizado.getNome(), dto.getNome());
	        assertEquals(clienteAtualizado.getSobrenome(), dto.getSobrenome());
	        assertEquals(clienteAtualizado.getCpf(), dto.getCpf());
	        assertEquals(clienteAtualizado.getDataNascimento(), Util.converterStringParaData(dto.getDataNascimento()));
	    }	    
	    
	    @Test
	    public void testCadastraCliente() throws Exception {

	    	ClienteDTO dto = UtilTest.initClienteDTO();
	        Cliente clienteEsperado = UtilTest.initCliente();
	        when(repository.save(any(Cliente.class))).thenReturn(clienteEsperado);
	        Cliente clienteResultado = service.cadastraCliente(dto);
	        assertEquals(clienteEsperado, clienteResultado);
	    }	    
	    
	    @Test
	    public void testObtemClientePorId() throws Exception {

	    	Cliente clienteEsperados = UtilTest.initCliente();
	        when(repository.findById(1L)).thenReturn(Optional.of(clienteEsperados));
	        Optional<Cliente> clienteResultado = service.obtemClientePorId(1L);
	        assertEquals(clienteEsperados, clienteResultado.get());
	    }
		    
	    @Test
	    public void testBuscaTodos() throws Exception {
	  
	    	Cliente cli1 = UtilTest.initCliente();
	    	Cliente cli2 = UtilTest.initCliente();	        
	        List<Cliente> clienteEsperados = Arrays.asList(cli1, cli2);
	        when(repository.findAll()).thenReturn(clienteEsperados);
	        Iterable<Cliente> produtosResultado = service.buscaTodos();
	        assertEquals(clienteEsperados, produtosResultado);
	    }	
	    
	    @Test
	    public void testExcluiClientePorId() {
	        Long id = 1L;
	        doNothing().when(repository).deleteById(id);
	        service.excluiClientePorId(id);
	        verify(repository, times(1)).deleteById(id);
	    }		    
	
}
