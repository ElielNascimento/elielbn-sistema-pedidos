package br.api.java.productapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.api.java.productapp.dto.ProdutoDTO;
import br.api.java.productapp.entities.Produto;
import br.api.java.productapp.repository.ProdutoRepository;
import br.api.java.productapp.service.ProdutoService;

@SpringBootTest

public class ProdutoServiceTest {

	private static final String DESCRICAO = "teste";

	private static final double PRECO = 100.0;

	private static final String NAME = "Eliel";

	private static final long ID = 1L;

	private static final int INDEX = 0;

	@InjectMocks
	private ProdutoService service;

	@Mock
	private ProdutoRepository repository;

	@Mock
	private Produto produto;

	@Mock
	private ProdutoDTO produtoDTO;

	private Optional<Produto> optionalProduto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		StartProduto();
	}

	@Test
	void whenFindAllTheReturnAnListOfPedidos() {
		Mockito.when(repository.findAllByOrderByPrecoDesc()).thenReturn(List.of(produto));

		List<ProdutoDTO> response = service.listar();

		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(ProdutoDTO.class, response.get(INDEX).getClass());

		Assertions.assertEquals(ID, response.get(INDEX).getId());
		Assertions.assertEquals(NAME, response.get(INDEX).getNome());
		Assertions.assertEquals(PRECO, response.get(INDEX).getPreco());
		Assertions.assertEquals(DESCRICAO, response.get(INDEX).getDescricao());
	}

	@Test
	void whenFindByIdThenReturnAnProdutoInstance() {

		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(optionalProduto);

		ProdutoDTO response = service.buscarPeloId(ID);
		Assertions.assertNotNull(response);

		Assertions.assertEquals(ProdutoDTO.class, response.getClass());
		Assertions.assertEquals(NAME, response.getNome());
		Assertions.assertEquals(ID, response.getId());
		Assertions.assertEquals(PRECO, response.getPreco());
		Assertions.assertEquals(DESCRICAO, response.getDescricao());
	}

	@Test
	void whenCreateThenReturnSuccess() {
		Mockito.when(repository.save(Mockito.any())).thenReturn(produto);

		ProdutoDTO response = service.adicionar(produtoDTO);

		Assertions.assertNotNull(response);

		Assertions.assertEquals(ProdutoDTO.class, response.getClass());
		Assertions.assertEquals(ID, response.getId());
		Assertions.assertEquals(NAME, response.getNome());
		Assertions.assertEquals(PRECO, response.getPreco());
		Assertions.assertEquals(DESCRICAO, response.getDescricao());
	}

	@Test
	void deleWhitSuccess() {

		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(optionalProduto);
		Mockito.doNothing().when(repository).deleteById(Mockito.anyLong());
		service.deletar(ID);
		Mockito.verify(repository, times(1)).deleteById(Mockito.anyLong());

	}

	@Test
	public void buscarProdutoPeloNome() {
		
	Mockito.when(repository.findByNome(Mockito.anyString())).thenReturn(List.of(produto));

	}

	private void StartProduto() {
		produto = new Produto(ID, NAME, PRECO, DESCRICAO);
		produtoDTO = new ProdutoDTO(ID, NAME, PRECO, DESCRICAO);
		optionalProduto = Optional.of(new Produto(ID, NAME, PRECO, DESCRICAO));
	}

}
