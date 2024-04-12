package br.com.infuse.crudsb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.infuse.crudsb.dto.PedidoMapperXML;
import br.com.infuse.crudsb.dto.PedidosDTO;
import br.com.infuse.crudsb.entities.Pedido;
import br.com.infuse.crudsb.exception.PedidoException;
import br.com.infuse.crudsb.repositories.PedidoRepository;
import br.com.infuse.crudsb.service.ClienteService;
import br.com.infuse.crudsb.service.PedidoService;
import br.com.infuse.crudsb.service.ProdutoService;
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
    void criaPedido_Teste() throws PedidoException {
        PedidosDTO dto = new PedidosDTO();
        dto.setNumControle(123L);
        dto.setCliente(1L);
        dto.setProduto(1L);
        dto.setQuantidade(5);

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
        double expected = 900.0;  // (100 * 0.90) * 10

        PedidoService pedidoService = new PedidoService();

        // Obtem o método privado usando reflexão
        Method method = PedidoService.class.getDeclaredMethod("calculaPrecoFinal", int.class, Double.class);
        method.setAccessible(true);  // Permite o acesso ao método privado
        // Chama o método privado
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
   
}
