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

import br.com.infuse.crudsb.dto.ProdutoDTO;
import br.com.infuse.crudsb.entities.Produto;
import br.com.infuse.crudsb.exception.ProdutoException;
import br.com.infuse.crudsb.repositories.ProdutoRepository;
import br.com.infuse.crudsb.service.ProdutoService;
import br.com.infuse.crudsb.util.UtilTest;

public class ProdutoServiceTest {

	   @InjectMocks
	    private ProdutoService service;

	    @Mock
	    private ProdutoRepository repository;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.initMocks(this);
	    }
	    
	    @Test
	    public void testAtualizaProduto() throws ProdutoException {
	    	ProdutoDTO dto = UtilTest.initProdutoDTO();

	        Produto produto = new Produto();
	        produto.setIdProduto(dto.getCodigo());
	        produto.setNome(dto.getNome());
	        produto.setPreco(dto.getPreco());

	        when(repository.findById(dto.getCodigo())).thenReturn(Optional.of(produto));
	        when(repository.save(any(Produto.class))).thenAnswer(i -> i.getArguments()[0]);

	        Produto produtoAtualizado = service.atualizaProduto(dto);

	        assertEquals(produtoAtualizado.getIdProduto(), dto.getCodigo());
	        assertEquals(produtoAtualizado.getNome(), dto.getNome());
	        assertEquals(produtoAtualizado.getPreco(), dto.getPreco());
	    }	    
	    
	    @Test
	    public void testCadastraProduto() throws Exception {

	        ProdutoDTO dto = UtilTest.initProdutoDTO();
	        Produto produtoEsperado = UtilTest.initProduto();
	        when(repository.save(any(Produto.class))).thenReturn(produtoEsperado);
	        Produto produtoResultado = service.cadastraProduto(dto);
	        assertEquals(produtoEsperado, produtoResultado);
	    }	    
	    
	    @Test
	    public void testObtemProdutoPorId() throws Exception {

	        Produto produtoEsperado = UtilTest.initProduto();
	        when(repository.findById(1L)).thenReturn(Optional.of(produtoEsperado));
	        Optional<Produto> produtoResultado = service.obtemProdutoPorId(1L);
	        assertEquals(produtoEsperado, produtoResultado.get());
	    }
		    
	    @Test
	    public void testBuscaTodos() throws Exception {
	  
	        Produto produto1 = UtilTest.initProduto();
	        Produto produto2 = UtilTest.initProduto();	        
	        List<Produto> produtosEsperados = Arrays.asList(produto1, produto2);
	        when(repository.findAll()).thenReturn(produtosEsperados);
	        Iterable<Produto> produtosResultado = service.buscaTodos();
	        assertEquals(produtosEsperados, produtosResultado);
	    }	
	    
	    @Test
	    public void testExcluiProdutoPorId() {
	        Long id = 1L;
	        doNothing().when(repository).deleteById(id);
	        service.excluiProdutoPorId(id);
	        verify(repository, times(1)).deleteById(id);
	    }	    
	
}
