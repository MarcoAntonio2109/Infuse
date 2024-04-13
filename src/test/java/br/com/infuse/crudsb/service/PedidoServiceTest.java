package br.com.infuse.crudsb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import br.com.infuse.crudsb.dto.PedidoDTO;
import br.com.infuse.crudsb.entitiy.Cliente;
import br.com.infuse.crudsb.entitiy.Pedido;
import br.com.infuse.crudsb.entitiy.Produto;
import br.com.infuse.crudsb.exception.PedidoException;
import br.com.infuse.crudsb.mapper.PedidoMapperXML;
import br.com.infuse.crudsb.repository.PedidoRepository;
import br.com.infuse.crudsb.specification.PedidoSpecification;
import br.com.infuse.crudsb.util.UtilTest;

public class PedidoServiceTest {

    @InjectMocks
    private PedidoService service;

    @Mock
    private PedidoRepository repository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {

        Pedido pedido = UtilTest.initPedido();
      
        when(repository.findById(anyLong())).thenReturn(Optional.of(pedido));
        Optional<Pedido> result = service.findById(anyLong());
        assertEquals(Optional.of(pedido), result);
        verify(repository, times(1)).findById(anyLong());
    }
    
    @Test
    public void testBuscaPedido() throws PedidoException {
    	
        Pedido pedido1 = UtilTest.initPedido();
        Pedido pedido2 = UtilTest.initPedido();
        List<Pedido> listaPedidos = Arrays.asList(pedido1, pedido2);

        Specification<Pedido> specification = new PedidoSpecification(any());
        when(repository.findAll(specification)).thenReturn(listaPedidos);

        List<Pedido> listaRetorno = service.buscaPedido(any());
        assertEquals(listaPedidos, listaRetorno);
    }    
    
    @Test
    public void testCriaPedidoXml() throws Exception {

        JAXBContext jaxbContext = mock(JAXBContext.class);
        Unmarshaller unmarshaller = mock(Unmarshaller.class);

        when(jaxbContext.createUnmarshaller()).thenReturn(unmarshaller);

        PedidoMapperXML pedidoMapperXML = new PedidoMapperXML();
        pedidoMapperXML.setCliente(1L);
        pedidoMapperXML.setProduto(1L);
        pedidoMapperXML.setNumControle(123L);
        pedidoMapperXML.setQuantidade(5);
        pedidoMapperXML.setData("2024-04-11");

        when(unmarshaller.unmarshal(any(StringReader.class))).thenReturn(pedidoMapperXML);

        Pedido pedidoEsperado = UtilTest.initPedido();

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<pedido>\r\n"
				+ "    <numControle>32479</numControle>\r\n"
				+ "    <produto>1</produto>\r\n"
				+ "    <quantidade>10</quantidade>\r\n"
				+ "    <cliente>1</cliente>\r\n"
				+ "    <data>2024-04-11T14:33:08</data>\r\n"
				+ "</pedido>";
		
        when(repository.validaNumControleExistente(anyLong())).thenReturn(0);
        when(clienteService.obtemClientePorId(anyLong())).thenReturn(Optional.of(UtilTest.initCliente()));
        when(produtoService.obtemProdutoPorId(anyLong())).thenReturn(Optional.of(UtilTest.initProduto()));
        when(repository.save(any())).thenReturn(UtilTest.initPedido());		

        Pedido pedidoResultado = service.criaPedidoXml(xml);

        assertEquals(pedidoEsperado.getNumControle(), pedidoResultado.getNumControle());
    }    
    
    @Test
    void testCriaPedido() throws PedidoException {
        PedidoDTO dto = UtilTest.initPedidoDTO();

        when(repository.validaNumControleExistente(dto.getNumControle())).thenReturn(0);
        when(clienteService.obtemClientePorId(dto.getCliente())).thenReturn(Optional.of(UtilTest.initCliente()));
        when(produtoService.obtemProdutoPorId(dto.getProduto())).thenReturn(Optional.of(UtilTest.initProduto()));
        when(repository.save(any())).thenReturn(UtilTest.initPedido());

        Pedido result = service.criaPedido(dto);

        assertNotNull(result);
        assertEquals(dto.getNumControle(), result.getNumControle());
        assertEquals(dto.getCliente(), result.getCliente().getId());
        assertEquals(dto.getProduto(), result.getProduto().getIdProduto());
        assertEquals(dto.getQuantidade(), result.getQuantidade());
        assertNotNull(result.getValor());
        assertNotNull(result.getDataCadastro());
    } 

    @Test
    void testCalculaPrecoFinal() throws Exception {
        int quantidade = 7;
        double preco = 100.0;
        double expected = 665.0; 

        PedidoService pedidoService = new PedidoService();
        
        Method method = PedidoService.class.getDeclaredMethod("calculaPrecoFinal", int.class, Double.class);
        method.setAccessible(true); 
        double result = (double) method.invoke(pedidoService, quantidade, preco);
        assertEquals(expected, result, 0.01);
    }

    @Test
    void testCalculaPrecoFinalQtdIgual10() throws Exception {
        int quantidade = 10;
        double preco = 100.0;
        double expected = 900.0;

        PedidoService pedidoService = new PedidoService();

        Method method = PedidoService.class.getDeclaredMethod("calculaPrecoFinal", int.class, Double.class);
        method.setAccessible(true); 

        double result = (double) method.invoke(pedidoService, quantidade, preco);

        assertEquals(expected, result, 0.01);
    }

    @Test
    void testCalculaPrecoFinalQtdMenor5() throws Exception {

        int quantidade = 3;
        double preco = 100.0;
        double expected = 300.0; 

        PedidoService pedidoService = new PedidoService();

        Method method = PedidoService.class.getDeclaredMethod("calculaPrecoFinal", int.class, Double.class);
        method.setAccessible(true);

        double result = (double) method.invoke(pedidoService, quantidade, preco);

        assertEquals(expected, result, 0.01);
    }

    @Test
    void testCalculaPrecoFinalQtdMaior10() throws Exception {

        int quantidade = 15;
        double preco = 100.0;
        double expected = 1500.0; 

        PedidoService pedidoService = new PedidoService();

        Method method = PedidoService.class.getDeclaredMethod("calculaPrecoFinal", int.class, Double.class);
        method.setAccessible(true);

        double result = (double) method.invoke(pedidoService, quantidade, preco);

        assertEquals(expected, result, 0.01);
    }	
    
    @Test
    void testNumControleExistenteException() throws PedidoException {
        PedidoDTO dto = UtilTest.initPedidoDTO();

        when(repository.validaNumControleExistente(dto.getNumControle())).thenReturn(1);

        assertThrows(PedidoException.class, () -> {
        	service.criaPedido(dto);
        });
    }   
    
    @Test
    void testObtemClienteException() throws PedidoException {
        PedidoDTO dto = UtilTest.initPedidoDTO();

        when(repository.validaNumControleExistente(dto.getNumControle())).thenReturn(0);
        //when(clienteService.obtemClientePorId(dto.getCliente())).thenReturn(Optional.of(new Cliente()));

        assertThrows(PedidoException.class, () -> {
        	service.criaPedido(dto);
        });
    }     
   
    @Test
    void testObtemProdutoException() throws PedidoException {
        PedidoDTO dto = UtilTest.initPedidoDTO();

        when(repository.validaNumControleExistente(dto.getNumControle())).thenReturn(0);
        when(clienteService.obtemClientePorId(dto.getCliente())).thenReturn(Optional.of(UtilTest.initCliente()));
       // when(produtoService.obtemProdutoPorId(dto.getProduto())).thenReturn(Optional.of(new Produto()));

        assertThrows(PedidoException.class, () -> {
        	service.criaPedido(dto);
        });
    } 
    
    @Test
    void testValidaQuantidadeException() throws PedidoException {
        PedidoDTO dto = UtilTest.initPedidoDTO();
        dto.setQuantidade(11);
        
        when(repository.validaNumControleExistente(dto.getNumControle())).thenReturn(0);
        when(clienteService.obtemClientePorId(dto.getCliente())).thenReturn(Optional.of(UtilTest.initCliente()));
        when(produtoService.obtemProdutoPorId(dto.getProduto())).thenReturn(Optional.of(UtilTest.initProduto()));

        assertThrows(PedidoException.class, () -> {
        	service.criaPedido(dto);
        });
    }      
}
